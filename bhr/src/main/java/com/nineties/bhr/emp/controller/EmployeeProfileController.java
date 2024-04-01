package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.EmployeeDTO;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees/dashboard")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @Autowired
    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") String id) {
        EmployeeDTO employee = employeeProfileService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") String id, @RequestBody EmployeeDTO employeeDetails) {
        EmployeeDTO updatedEmployee = employeeProfileService.updateEmployee(id, employeeDetails);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/introduction")
    public ResponseEntity<Void> deleteIntroduction(@PathVariable("id") String id) {
        boolean isDeleted = employeeProfileService.deleteIntroduction(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
