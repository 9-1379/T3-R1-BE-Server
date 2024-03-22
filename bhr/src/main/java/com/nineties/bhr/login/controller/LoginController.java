package com.nineties.bhr.login.controller;


import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.dto.JoinDTO;
import com.nineties.bhr.emp.service.JoinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping("/login")
    public String login_form() {
        System.out.println("받음");
        return "Hello from Spring!";
    }

    private final JoinService joinService;

    public LoginController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/login/join")
    public String joinProcess(JoinDTO joinDTO) {

        joinDTO.setName("aaa");
        joinDTO.setEmpNo(1L);
        joinDTO.setGender(Gender.FEMALE);
        joinDTO.setBirthday("19991122");
        joinDTO.setPhoneNumber("01042991435");
        joinDTO.setPosition("aaa");
        joinDTO.setJobId("aaa");
        joinDTO.setHireDate(new Date());

        System.out.println(joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }

}
