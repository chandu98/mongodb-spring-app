package com.ftd.example.SpringBootMongoDBApplication.repository;

import com.ftd.example.SpringBootMongoDBApplication.model.TodoDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Configuration
public interface TodoRepository extends MongoRepository<TodoDTO, String> {

    @Query("{'todo': ?0}")       // 0th index means first parameter which is passed in the function
    Optional<TodoDTO> findByTodo(String todo);

}
