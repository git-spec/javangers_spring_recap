package com.example.javangers_spring_recap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo taskRepo;
    private final IDService idService;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }
}
