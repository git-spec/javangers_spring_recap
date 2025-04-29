package com.example.javangers_spring_recap.model;

import java.util.List;


public record ChatGPTResponse(List<ChatGPTChoice> choices) {

}
