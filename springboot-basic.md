<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Basic CRUD Spring Boot Application

Here's a minimal Spring Boot app that performs CRUD (Create, Read, Update, Delete) operations on a simple `Item` entity. The code includes comments explaining its working.

***

```java
// Main Application Class
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Marks this as a Spring Boot app
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);  // Starts the app
    }
}
```

```java
// Entity Class representing database table 'items'
package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity  // Maps the class to a DB table
public class Item {

    @Id  // Marks primary key
    @GeneratedValue  // Auto-generates ID values
    private Long id;
    private String name;

    public Item() {}  // Default constructor required by JPA

    public Item(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

```java
// Repository Interface for database operations
package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // No methods needed, JpaRepository provides CRUD methods automatically
}
```

```java
// REST Controller exposing CRUD APIs
package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // Marks as REST API controller
@RequestMapping("/items")  // Base URL for all item endpoints
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;  // Inject repository for DB access
    }

    // Create a new Item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return repository.save(item);  // Saves item in DB and returns it
    }

    // Get all Items
    @GetMapping
    public List<Item> getItems() {
        return repository.findAll();  // Retrieves all items from DB
    }

    // Get a single Item by id
    @GetMapping("/{id}")
    public Optional<Item> getItem(@PathVariable Long id) {
        return repository.findById(id);  // Finds by id or empty if not found
    }

    // Update an existing Item
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item newItem) {
        return repository.findById(id)
          .map(item -> {
              item.setName(newItem.getName());  // Update fields
              return repository.save(item);  // Save updated entity
          })
          .orElseGet(() -> {
              newItem.setId(id);  // If not found, create new with given id
              return repository.save(newItem);
          });
    }

    // Delete Item by id
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);  // Delete by id
    }
}
```


***

### How it works

- The **`DemoApplication`** class starts the Spring Boot app.
- The **`Item`** entity maps to a database table, with fields and an auto-generated primary key.
- The **`ItemRepository`** extends `JpaRepository` giving built-in CRUD methods.
- The **`ItemController`** exposes REST endpoints:
    - `POST /items` to create
    - `GET /items` to list all
    - `GET /items/{id}` to get one
    - `PUT /items/{id}` to update
    - `DELETE /items/{id}` to delete
- Spring Data JPA handles database interaction transparently based on entity and repository.
- You can run this app, and interact via HTTP requests with a REST client like Postman.

Would you like help setting this up step-by-step or creating example requests? Feel free to ask!

