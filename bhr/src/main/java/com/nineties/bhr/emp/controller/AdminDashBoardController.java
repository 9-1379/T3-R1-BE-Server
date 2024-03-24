package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.AdminDTO;
import com.nineties.bhr.emp.service.AdminDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminDashBoardController {
    private final AdminDashBoardService adminDashBoardService;

    @Autowired
    public AdminDashBoardController(AdminDashBoardService adminDashBoardService) {
        this.adminDashBoardService = adminDashBoardService;
    }

//    @GetMapping("/count")
//    public ResponseEntity<Long> getEmployeeCount() {
//        Long count = adminDashBoardService.getEmployeeCount();
//        return ResponseEntity.ok().body(count);
//    }

//    @GetMapping(value = "/list", produces = { MediaType.APPLICATION_JSON_VALUE })
//    public ResponseEntity<AdminDTO> employeesList() {
//        return (ResponseEntity<AdminDTO>) adminDashBoardService.getAllEmployees();
//    }

    @GetMapping("/list")
    public List<AdminDTO> getAllEmployees() {


        return adminDashBoardService.getAllEmployees();
    }

//    @GetMapping
//    public List<>
}
