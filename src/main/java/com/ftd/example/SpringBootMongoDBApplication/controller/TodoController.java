package com.ftd.example.SpringBootMongoDBApplication.controller;

import com.ftd.example.SpringBootMongoDBApplication.exception.TodoCollectionException;
import com.ftd.example.SpringBootMongoDBApplication.exception.TodoNotNullException;
import com.ftd.example.SpringBootMongoDBApplication.model.TodoDTO;
import com.ftd.example.SpringBootMongoDBApplication.repository.TodoRepository;
import com.ftd.example.SpringBootMongoDBApplication.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List <TodoDTO> todos = todoService.getAllTodos();

        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);

    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {

            try {
                todoService.createTodo(todo);
                return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
            } catch (ConstraintViolationException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            } catch (TodoCollectionException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }

    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id)
    {

        try{
            return new ResponseEntity<>(todoService.geTodoById(id), HttpStatus.OK);
        }
        catch (TodoCollectionException e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        /*Optional<TodoDTO> todoOptional = todoRepository.findById(id);  // findById returns Optional type

        if (todoOptional.isPresent()){
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Todo not found with Id " + id, HttpStatus.NOT_FOUND);
        }*/


    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo) throws TodoCollectionException {
        try {
            todoService.updateById(id, todo);
            return new ResponseEntity<>("Updated Todo with id "+id, HttpStatus.OK);
        }
        catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
       /* Optional<TodoDTO> todoOptional = todoRepository.findById(id);  // findById returns Optional type

        if (todoOptional.isPresent()){
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));

            todoRepository.save(todoToSave);

            return new ResponseEntity<>(todoToSave,HttpStatus.OK);*/

    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id) throws TodoCollectionException {
        try {
            todoService.deleteByTodoId(id);
            return new ResponseEntity<>("Successfully deleted the todo with Id "+id,HttpStatus.OK);
        }
        catch (TodoCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        /*try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted with Id " + id, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }*/
    }


}
