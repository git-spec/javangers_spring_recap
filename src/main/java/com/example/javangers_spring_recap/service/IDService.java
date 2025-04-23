package com.example.javangers_spring_recap.service;

import java.util.UUID;

import org.springframework.stereotype.Service;


@Service
public class IDService {

    public String createID() {
        return UUID.randomUUID().toString();
    }
}
