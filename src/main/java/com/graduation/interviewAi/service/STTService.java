package com.graduation.interviewAi.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class STTService {

    @Value("${gcp.credentials.path}")
    private String gcpCredentialsPath;

    public String transcribeAudio(String fileName) throws Exception {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(gcpCredentialsPath));
        SpeechSettings speechSettings = SpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (SpeechClient speechClient = SpeechClient.create(speechSettings)) {
            ClassPathResource audioResource = new ClassPathResource("audio/" + fileName);
            InputStream audioStream = audioResource.getInputStream();
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
