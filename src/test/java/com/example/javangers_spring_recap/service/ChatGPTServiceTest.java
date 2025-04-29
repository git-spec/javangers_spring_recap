package com.example.javangers_spring_recap.service;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(properties = { "API_KEY=123" })
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
public class ChatGPTServiceTest {
    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkForSpellingErrors_shouldReturnResponse_whenSendRequest() throws Exception {
        // GIVEN
        mockServer
            .expect(requestTo("https://api.openai.com/v1/chat/completions"))
            .andExpect(method(HttpMethod.POST))
            .andExpect(content().json(
                """
                    {
                        "model": "gpt-4.1",
                        "messages": [
                            {
                                "role": "user",
                                "content": "Bitte gib den folgenden Text ohne Rechtschreibfehler zurück: Teilname an Beneftzferanstaltung"
                            }
                        ]
                    }       
                """
            ))
            .andRespond(withSuccess(
                """
                    {
                        "choices": [
                            {
                                "message": {
                                    "role": "assistant",
                                    "content": "Teilnahme an Benefizveranstaltung"
                                }
                            }
                        ]
                    }   
                """,
                MediaType.APPLICATION_JSON
            )
        );
        // WHEN & THEN
        mockMvc
            .perform(
                post("http://localhost:8080/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                            "description": "Teilname an Beneftzferanstaltung",
                            "status": "OPEN"
                        }       
                    """
                )
            )
            .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(
                """
                    {
                        "description": "Teilnahme an Benefizveranstaltung",
                        "status": "OPEN"
                    }   
                """
            )
        );
    }
}
