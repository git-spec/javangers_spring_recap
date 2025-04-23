package com.example.javangers_spring_recap.dto;

import com.example.javangers_spring_recap.model.Status;


public record TaskDTO(String description, Status status) {

}
