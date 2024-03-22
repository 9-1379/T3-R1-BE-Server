package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.service.AdminDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class AdminDashBoardController {
    private final AdminDashBoardService adminDashBoardService;

    @Autowired
    public AdminDashBoardController(AdminDashBoardService adminDashBoardService) {
        this.adminDashBoardService = adminDashBoardService;
    }

    @GetMapping("/count")
    public Long getEmployeeCount() {
        return adminDashBoardService.getEmployeeCount();
    }
}
