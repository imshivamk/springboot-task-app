//package com.shivam.todoBackend.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class testDbController {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @GetMapping("/manual-create-todo")
//    public String createTableAndInsert(){
//        String createTableSql = """
//                CREATE TABLE IF NOT EXISTS todo (
//                                id SERIAL PRIMARY KEY,
//                                title VARCHAR(255) NOT NULL,
//                                description TEXT,
//                                completed BOOLEAN DEFAULT FALSE
//                            )
//                """;
//        jdbcTemplate.execute(createTableSql);
//        String insertSql = """
//                INSERT INTO todo(title, description, completed)\s
//                VALUES ('Manual Todo', 'Created manually via JdbcTemplate', false)
//                """;
//        jdbcTemplate.update(insertSql);
//
//        return "Table created and testRecordInserted!";
//    }
//}
