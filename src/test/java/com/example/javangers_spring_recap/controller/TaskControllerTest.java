package com.example.javangers_spring_recap.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.javangers_spring_recap.model.Status;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;


@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepo repo;

    @Test
    void testGetAllTasks_shouldReturnListOfTasks_whenIsBeenCalled() throws Exception {
        // GIVEN
        Task task = new Task("1", "blabla", Status.OPEN);
        repo.save(task);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                """
                    [
                        {
                            "id": "1",
                            "description": "blabla",
                            "status": "TODO"
                        }
                    ]        
                """
            ));
    }
}
