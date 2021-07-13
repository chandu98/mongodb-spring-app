package com.ftd.example.SpringBootMongoDBApplication.service;

import com.ftd.example.SpringBootMongoDBApplication.exception.TodoCollectionException;
import com.ftd.example.SpringBootMongoDBApplication.exception.TodoNotNullException;
import com.ftd.example.SpringBootMongoDBApplication.model.TodoDTO;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface TodoService {

    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException;

    public List<TodoDTO> getAllTodos();

    public TodoDTO geTodoById(String id) throws TodoCollectionException;

    public void updateById(String id, TodoDTO todo) throws TodoCollectionException;

    public void deleteByTodoId(String id) throws TodoCollectionException;
}
