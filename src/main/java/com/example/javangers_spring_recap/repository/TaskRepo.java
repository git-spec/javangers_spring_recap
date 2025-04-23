package com.example.javangers_spring_recap.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.javangers_spring_recap.model.Task;


@Repository
public interface TaskRepo extends MongoRepository<Task, String> {}
