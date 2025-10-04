package com.shivam.todoBackend.controller;

import com.shivam.todoBackend.DataTransferObjects.ApiResponse;
import com.shivam.todoBackend.model.Todo;
import com.shivam.todoBackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    // crud

    //create
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoRepository.save(todo);
    }

    // read
    @GetMapping
    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }
    // read by id
    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id){
        return todoRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Todo not found!"
                ));
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails){
        Optional<Todo> todoOptional = todoRepository.findById(id);
        if(!todoOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Todo todo = todoOptional.get();
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return ResponseEntity.ok(updatedTodo);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTodo(@PathVariable Long id){
        Optional<Todo> todo = todoRepository.findById(id);
        if(!todo.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Todo not found"));
        }
        todoRepository.delete(todo.get());
        return ResponseEntity.ok(new ApiResponse(true, "Task deleted Successfully!"));
    }

}
