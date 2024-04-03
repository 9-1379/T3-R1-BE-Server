package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp/dashboard")
public class EmployeeProfileController {

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeById(@PathVariable("id") String id) {
        EmployeeProfileDTO employeeProfile = employeeProfileService.getEmployeeById(id);
        if (employeeProfile != null) {
            return ResponseEntity.ok(employeeProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeProfileDTO> updateEmployeeIntroduction(@PathVariable("id") String id, @RequestBody EmployeeProfileDTO employeeProfileDTO) {
        EmployeeProfileDTO updatedEmployeeProfile = employeeProfileService.updateEmployeeIntroduction(id, employeeProfileDTO.getIntroduction());
        if (updatedEmployeeProfile != null) {
            return ResponseEntity.ok(updatedEmployeeProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

