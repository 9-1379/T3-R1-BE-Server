package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.service.EmployeesProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeesProfileController {

private final EmployeesProfileService employeesProfileService;

@Autowired
public EmployeesProfileController(EmployeesProfileService employeesProfileService) {
    this.employeesProfileService = employeesProfileService;
}

@GetMapping("/{id}")
public ResponseEntity<Map<String, Object>> getEmployeeById(@PathVariable("id") String id) {
    Employees employee = employeesProfileService.getEmployeeById(id);
    if (employee == null) {
        return ResponseEntity.notFound().build();
    } else {
        Map<String, Object> response = new HashMap<>();
        response.put("name", employee.getName());
        response.put("department", employee.getDept() != null ? employee.getDept().getId() : null);
        response.put("position", employee.getPosition());

        return ResponseEntity.ok(response);
    }
}

@PutMapping("/{id}")
public ResponseEntity<Employees> updateEmployee(@PathVariable String id, @RequestBody Employees employeeDetails) {
    Employees updatedEmployee = employeesProfileService.updateEmployee(id, employeeDetails);
    return ResponseEntity.ok(updatedEmployee);
}

@DeleteMapping("/{id}/introduction")
public ResponseEntity<Void> deleteIntroduction(@PathVariable String id) {
    boolean isDeleted = employeesProfileService.deleteIntroduction(id);
    return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

    }
}
