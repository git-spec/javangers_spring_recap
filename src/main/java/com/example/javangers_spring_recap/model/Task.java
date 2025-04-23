package com.example.javangers_spring_recap.model;

import org.springframework.data.mongodb.core.mapping.Document;


@Document("Tasks")
public record Task(String id, String description, Status status) {

}
