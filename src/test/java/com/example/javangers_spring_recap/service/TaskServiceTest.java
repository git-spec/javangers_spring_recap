package com.example.javangers_spring_recap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.javangers_spring_recap.model.Status;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;


public class TaskServiceTest {
    TaskRepo mockRepo = mock(TaskRepo.class);
    TaskService service = new TaskService(mockRepo);

    @Test
    void testGetAllTasks_shouldReturnAllTasks_whenIsBeenCalled() {
        // GIVEN
        List<Task> expected = List.of(new Task("1", "blabla", Status.TODO));
        when(mockRepo.findAll()).thenReturn(expected);
        // WHEN
        List<Task> actual = service.getAllTasks();
        // THEN
        assertEquals(expected, actual);
    }
}
