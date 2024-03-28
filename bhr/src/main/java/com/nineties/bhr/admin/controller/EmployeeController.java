package com.nineties.bhr.admin.controller;

import com.nineties.bhr.admin.dto.EmployeeDTO;
import com.nineties.bhr.admin.service.EmployeeService; // Importing EmployeeService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService; // Autowiring EmployeeService

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.findAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{employeeId}/retire")
    public ResponseEntity<?> retireEmployee(@PathVariable String employeeId) {
        try {
            employeeService.retireEmployee(employeeId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/retire-multiple")
    public ResponseEntity<?> retireMultipleEmployees(@RequestBody List<String> employeeIds) {
        try {
            employeeService.retireMultipleEmployees(employeeIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retiring employees: " + e.getMessage());
        }
    }
}
