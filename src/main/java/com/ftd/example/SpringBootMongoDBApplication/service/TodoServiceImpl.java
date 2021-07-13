package com.ftd.example.SpringBootMongoDBApplication.service;

import com.ftd.example.SpringBootMongoDBApplication.exception.TodoCollectionException;
import com.ftd.example.SpringBootMongoDBApplication.exception.TodoNotNullException;
import com.ftd.example.SpringBootMongoDBApplication.model.TodoDTO;
import com.ftd.example.SpringBootMongoDBApplication.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        // If the Todo is already present it will throw an error

        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());

        if(todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        }
        else{
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }

    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll();
        if(todos.size() > 0){
            return todos;
        }
        else{
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO geTodoById(String id) throws TodoCollectionException{
        Optional <TodoDTO> todoOptional = todoRepository.findById(id);
        if(!todoOptional.isPresent()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
        else{
            return todoOptional.get();
        }
    }

    @Override
    public void updateById(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepository.findByTodo(todo.getTodo());

        if(todoOptional.isPresent()){

            if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)){
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            TodoDTO todoToUpdate = todoOptional.get();

            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.getCompleted());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToUpdate);
        }
        else{
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteByTodoId(String id) throws TodoCollectionException {
        Optional<TodoDTO> todoDelete = todoRepository.findById(id);
        if (todoDelete.isEmpty()){
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
        else{
            todoRepository.deleteById(id);
        }
    }


}
