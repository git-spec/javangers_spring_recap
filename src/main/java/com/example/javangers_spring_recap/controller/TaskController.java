package com.example.javangers_spring_recap.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.javangers_spring_recap.dto.TaskDTO;
import com.example.javangers_spring_recap.exception.TaskNotFoundException;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.service.TaskService;


@RestController
@RequestMapping("/api")
public class TaskController {
    TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    public List<Task> getTasks(@RequestParam(required = false) String status) throws TaskNotFoundException {
        if (status != null) {
            return service.getTasksByStatus(status);
        } else {
            return service.getAllTasks();
        }
    }


    private List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    private List<Task> getTasksByStatus(@RequestParam(required = false) String status) throws Exception {
        return service.getTasksByStatus(status);
    }

    @GetMapping("/todo/{id}")
    public Task getTaskByID(@PathVariable String id) throws TaskNotFoundException {
        return service.getTaskByID(id);
    }

    @PostMapping("/todo")
    public Task addTask(@RequestBody TaskDTO taskDTO) {
        return service.addTask(taskDTO);
    }

    @PutMapping("/todo/{id}")
    public Task updateTask(@RequestBody Task updatedTask) throws TaskNotFoundException {
        return service.updateTask(updatedTask);
    }

    @DeleteMapping("/todo/{id}")
    public Task deleteTask(@PathVariable String id) throws TaskNotFoundException {
        return service.deleteTask(id);
    }
}
