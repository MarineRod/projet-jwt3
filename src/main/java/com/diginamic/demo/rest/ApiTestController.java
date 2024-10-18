package com.diginamic.demo.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class ApiTestController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
