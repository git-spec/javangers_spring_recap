package com.example.javangers_spring_recap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.javangers_spring_recap.dto.TaskDTO;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.service.TaskService;


@RestController
@RequestMapping("/api")
public class TaskController {
    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping("/task")
    public Task addTask(TaskDTO taskDTO) {
        return service.addTask(taskDTO);
    }
}
