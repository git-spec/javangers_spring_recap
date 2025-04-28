package com.example.javangers_spring_recap.model;

import com.example.javangers_spring_recap.exception.TaskNotFoundException;


public enum Status {
    OPEN("open"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public static Status readValue(String value) throws TaskNotFoundException {
        for (Status status: values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            } else {
                throw new TaskNotFoundException("No enum constant " + value + '.');
            }
        }
        return null;
    }
}