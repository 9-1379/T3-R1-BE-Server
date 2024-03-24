package com.nineties.bhr.admin.controller;

import com.nineties.bhr.admin.dto.AdminDTO;
import com.nineties.bhr.emp.service.AdminDashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class AdminDashBoardController {
    private final AdminDashBoardService adminDashBoardService;

    @Autowired
    public AdminDashBoardController(AdminDashBoardService adminDashBoardService) {
        this.adminDashBoardService = adminDashBoardService;
    }

    //    @GetMapping("/list")
//    public List<AdminDTO> getAllEmployees() {
//        return adminDashBoardService.getAllEmployees();
//    }
    @GetMapping("/list")
    public Map<String, Object> getEmployees() {
        Map<String, Object> result = new HashMap<>();
        List<AdminDTO> employeeList = adminDashBoardService.getAllEmployees();
        Long count = adminDashBoardService.getCount();

        result.put("employeeList", employeeList);
        result.put("count", count);

        return result;
    }
}

