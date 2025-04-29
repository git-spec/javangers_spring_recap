package com.example.javangers_spring_recap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.javangers_spring_recap.model.ChatGPTMessage;
import com.example.javangers_spring_recap.model.ChatGPTRequest;
import com.example.javangers_spring_recap.model.ChatGPTResponse;



@Service
public class ChatGPTService {
    private final RestClient restClient;

    public ChatGPTService(RestClient.Builder builder, @Value("${API_KEY}") String apiKey) {
        this.restClient = builder
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Authorization", "Bearer " + apiKey)
            .build();
    }

    public String checkForSpellingErrors(String content) {
        ChatGPTMessage message = new ChatGPTMessage(
            "user", 
            "Bitte gib den folgenden Text ohne Rechtschreibfehler zurück: " + content
        );
        ChatGPTRequest request = new ChatGPTRequest("gpt-4.1", List.of(message));
        ChatGPTResponse response = restClient
            .post()
            .accept(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .body(ChatGPTResponse.class);
        return response.choices().getFirst().message().content();
    }
}
