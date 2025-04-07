package com.graduation.interviewAi.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class STTService {

    @Value("${gcp.credentials.path}")
    private String gcpCredentialsPath;

    // STT 처리
    public String transcribeAudio(String absolutePath) throws Exception {
        // 변환된 파일 경로
        File convertedFile = File.createTempFile("converted_", ".wav");

        // ffmpeg로 변환
        convertToSTTCompatibleFormat(new File(absolutePath), convertedFile);

        // GCP STT 처리
        String sttResult = transcribeConvertedFile(convertedFile.getAbsolutePath());
        System.out.println("변환된 텍스트:\n" + sttResult);

        return sttResult;
    }


    // ffmpeg 변환 method (.wav 포맷에 맞게 변환)
    private void convertToSTTCompatibleFormat(File original, File converted) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-y",
                "-i", original.getAbsolutePath(),
                "-ar", "16000",
                "-ac", "1",
                "-c:a", "pcm_s16le",
                converted.getAbsolutePath()
        );

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[ffmpeg] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("ffmpeg 변환 실패 (exitCode=" + exitCode + ")");
        }
    }

    // Google Speech-to-Text 요청 method
    private String transcribeConvertedFile(String absolutePath) throws Exception {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(gcpCredentialsPath));
        SpeechSettings speechSettings = SpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (SpeechClient speechClient = SpeechClient.create(speechSettings)) {
            InputStream audioStream = new FileInputStream(absolutePath);
            ByteString audioBytes = ByteString.readFrom(audioStream);

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(16000)
                    .setLanguageCode("ko-KR")
                    .build();

            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            StringBuilder resultText = new StringBuilder();

            for (SpeechRecognitionResult result : response.getResultsList()) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                resultText.append(alternative.getTranscript()).append("\n");
            }

            return resultText.toString().trim();
        }
    }
}
