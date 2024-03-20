package com.nineties.bhr.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
//    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String greeting() {
        return "Hello from Spring Boot";
    }
}
