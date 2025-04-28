package com.example.javangers_spring_recap.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.javangers_spring_recap.model.Status;
import com.example.javangers_spring_recap.model.Task;
import com.example.javangers_spring_recap.repository.TaskRepo;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)    // Empties repo.
public class TaskControllerTest {
    Task task = new Task("1", "blabla", Status.OPEN);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepo repo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTasks_shouldReturnListOfTasks_whenIsBeenCalled() throws Exception {
        // GIVEN
        repo.save(task);
        // WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                """
                    [
                        {
                            "id": "1",
                            "description": "blabla",
                            "status": "OPEN"
                        }
                    ]        
                """
            ));
    }

    // Tests on the one hand the retrieval of task and on the other the random id.
    @Test
    void getTaskByID_shouldReturnTask_whenCalledByID() throws Exception {
        MvcResult postResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
        .contentType(MediaType.APPLICATION_JSON)
        .content(
            """
            {
                "description": "blabla",
                "status": "OPEN"
            }
            """
        ))
        .andReturn();

        String postResultString = postResult.getResponse().getContentAsString();
        Task createTask = objectMapper.readValue(postResultString, Task.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + createTask.id()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(String.valueOf(objectMapper.writeValueAsString(createTask))));
    }

    @Test
    void getTaskByID_shouldReturnException_whenCalledByInvalidID() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/" + "123"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Task with id 123 could not been found."));
    }

    @Test
    void addTask_shouldReturnTask_whenGetData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "description": "blabla",
                        "status": "OPEN"
                    }     
                """
            ))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                """
                    {
                        "description": "blabla",
                        "status": "OPEN"
                    }     
                """
            ))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void updateTask_shouldReturnTask_whenGetUpdatedTask() throws Exception {
        // GIVEN
        repo.save(task);
        // WHEN AND THEN
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/" + task.id())
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                """
                    {
                        "id": "1",
                        "description": "blabla",
                        "status": "IN_PROGRESS"
                    }        
                """
            ))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                """
                    {
                        "id": "1",
                        "description": "blabla",
                        "status": "IN_PROGRESS"
                    }        
                """
            ));
    }

    @Test
    void deleteTask_shouldReturnDeletedTask_whenGetID() throws Exception {
        // GIVEN
        repo.save(task);
        // WHEN AND THEN
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/" + task.id()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(
            """
                {
                    "id": "1",
                    "description": "blabla",
                    "status": "OPEN"
                }        
            """
        ));
    }
}
