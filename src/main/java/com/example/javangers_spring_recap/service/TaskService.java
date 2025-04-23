package com.example.javangers_spring_recap.service;

import org.springframework.stereotype.Service;

import com.example.javangers_spring_recap.repository.TaskRepo;


@Service
public class TaskService {
    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }
}
