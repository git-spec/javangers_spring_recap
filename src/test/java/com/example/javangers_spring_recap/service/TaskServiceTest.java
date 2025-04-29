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
    ChatGPTService gptService = Mockito.mock(ChatGPTService.class);
    TaskService service = new TaskService(mockRepo, mockID, gptService);
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

    @Test
    void updateTask_shouldReturnTask_whenGetData() throws TaskNotFoundException {
        // GIVEN
        Task expected = new Task("1", "blabla", Status.IN_PROGRESS);
        Mockito.when(mockRepo.existsById(id)).thenReturn(true);
        Mockito.when(mockRepo.save(expected)).thenReturn(expected);
        // WHEN
        Task actual = service.updateTask(expected);
        // THEN
        assertEquals(expected, actual);
        Mockito.verify(mockRepo).save(actual);
    }

    @Test
    void updateTask_shouldThrowExpection_whenGetInvalidData() {
        // GIVEN
        Task expected = new Task("2", "blabla", Status.IN_PROGRESS);
        Mockito.when(mockRepo.existsById("2")).thenReturn(false);
        Mockito.when(mockRepo.save(expected)).thenReturn(expected);
        // WHEN
        try {
            service.updateTask(expected);
            fail();
        // THEN
        } catch (TaskNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void deleteTask_shouldReturnTrue_whenGetID() throws TaskNotFoundException {
        // GIVEN
        Mockito.when(mockRepo.existsById(id)).thenReturn(true);
        Mockito.when(mockRepo.findById(id)).thenReturn(Optional.of(task));
        // WHEN
        Task actual = service.deleteTask(id);
        // THEN
        assertEquals(task, actual);
    }    

    @Test
    void deleteTask_shouldThrowException_whenGetInvalidID() {
        // GIVEN
        Mockito.when(mockRepo.existsById("2")).thenReturn(false);
        Mockito.when(mockRepo.findById("2")).thenReturn(Optional.empty());
        // WHEN
        try {
            service.deleteTask("2");
            fail();
        // THEN
        } catch (TaskNotFoundException e) {
            assertTrue(true);
        }
    }
}
