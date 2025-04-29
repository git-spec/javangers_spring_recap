package com.example.javangers_spring_recap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.javangers_spring_recap.dto.TaskDTO;
import com.example.javangers_spring_recap.exception.TaskNotFoundException;
import com.example.javangers_spring_recap.model.ChatGPTMessage;
import com.example.javangers_spring_recap.model.ChatGPTRequest;
import com.example.javangers_spring_recap.model.ChatGPTResponse;
import com.example.javangers_spring_recap.model.Status;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo taskRepo;
    private final IDService idService;
    private final ChatGPTService gptService;

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task getTaskByID(String id) throws TaskNotFoundException {
        return taskRepo.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " could not been found."));
    }

    public List<Task> getTasksByStatus(String status) throws TaskNotFoundException {
        Status reqStatus = Status.readValue(status);
        return taskRepo.findAll().stream()
            .filter(task -> task.status().equals(reqStatus))
            .toList();
    }

    public Task addTask(TaskDTO task) {
        Task newTask = new Task(
            idService.createID(),
            gptService.checkForSpellingErrors(task.description()),
            task.status()
        );
        return taskRepo.save(newTask);
    }

    public Task updateTask(Task updatedTask) throws TaskNotFoundException {
        if (taskRepo.existsById(updatedTask.id())) {
            return taskRepo.save(updatedTask);
        } else {
            throw new TaskNotFoundException("Task with id " + updatedTask.id() + " could not been found.");
        }
    }

    public Task deleteTask(String id) throws TaskNotFoundException {
        if (taskRepo.existsById(id)) {
            Task deletedTask = taskRepo.findById(id).get();
            taskRepo.deleteById(id);
            return deletedTask;
        } else {
            throw new TaskNotFoundException("Task with id " + id + " could not been found.");
        }
    }
}
