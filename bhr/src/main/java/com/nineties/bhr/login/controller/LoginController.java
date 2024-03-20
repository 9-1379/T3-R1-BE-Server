package com.nineties.bhr.login.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/login")
    public String login_form() {
        System.out.println("받음");
        return "Hello from Spring!";
    }

}
