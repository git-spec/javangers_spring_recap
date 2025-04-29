package com.example.javangers_spring_recap.model;

import java.util.List;


public record ChatGPTRequest(String model, List<ChatGPTMessage> messages) {}
