package com.example.javangers_spring_recap.service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.javangers_spring_recap.dto.TaskDTO;
import com.example.javangers_spring_recap.exception.TaskNotFoundException;
import com.example.javangers_spring_recap.model.Status;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;


public class TaskServiceTest {
    TaskRepo mockRepo = Mockito.mock(TaskRepo.class);
    IDService mockID = Mockito.mock(IDService.class);
    TaskService service = new TaskService(mockRepo, mockID);
    Task task = new Task("1", "blabla", Status.OPEN);
    TaskDTO taskDTO = new TaskDTO("blabla", Status.OPEN);
    String id = "1";

    @Test
    void getAllTasks_shouldReturnEmptyList_whenIsBeenCalledInitially() {
        // GIVEN
        List<Task> expected = Collections.emptyList();
        Mockito.when(mockRepo.findAll()).thenReturn(expected);
        // WHEN
        List<Task> actual = service.getAllTasks();
        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllTasks_shouldReturnListOfTasks_whenIsBeenCalled() {
        // GIVEN
        List<Task> expected = List.of(task);
        Mockito.when(mockRepo.findAll()).thenReturn(expected);
        // WHEN
        List<Task> actual = service.getAllTasks();
        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void getTaskByID_shouldReturnTask_whenGetID() throws TaskNotFoundException {
        // GIVEN
        Mockito.when(mockID.createID()).thenReturn(id);
        Mockito.when(mockRepo.findById(id)).thenReturn(Optional.of(task));
        // WHEN
        Task actual = service.getTaskByID(id);
        // THEN
        assertEquals(task, actual);
    }

    @Test
    void getTaskByID_shouldThrowException_whenGetInvalidID() {
        // GIVEN
        Mockito.when(mockRepo.findById("2")).thenReturn(Optional.empty());
        // WHEN
        try {
            service.getTaskByID("2");
            fail();
        // THEN
        } catch(TaskNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void addTask_shouldReturnTask_whenGetData() {
        // GIVEN
        Mockito.when(mockID.createID()).thenReturn("1");
        Mockito.when(mockRepo.save(task)).thenReturn(task);
        // WHEN
        Task actual = service.addTask(taskDTO);
        // THEN
        assertEquals(task, actual);
        Mockito.verify(mockRepo).save(actual);
    }
}
