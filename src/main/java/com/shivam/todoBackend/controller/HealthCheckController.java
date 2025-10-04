package com.shivam.todoBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String healthCheck(){

        return "API working successfully!";
    }
}
