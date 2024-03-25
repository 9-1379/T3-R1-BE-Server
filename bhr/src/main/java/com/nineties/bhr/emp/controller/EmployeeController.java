package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employees> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable String id) {
        Employees employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok().body(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    // 새로운 엔드포인트 추가
//    @PutMapping("/{employeeId}/retire")
//    public void retireEmployee(@PathVariable String employeeId) {
//        employeeService.retireEmployee(employeeId);
//    }
}
