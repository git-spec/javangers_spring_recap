package com.example.javangers_spring_recap.exception;

public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
