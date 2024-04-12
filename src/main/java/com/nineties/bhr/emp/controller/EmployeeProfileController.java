package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.dto.EmployeeProfileProjection;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/emp/dashboard")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @Autowired
    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

    @GetMapping
    public EmployeeProfileProjection getEmployeeByUsername() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return employeeProfileService.getEmployeeByUsername(username);
    }

    @PostMapping("/empIntro")
    public ResponseEntity<EmployeeProfileDTO> updateEmployeeIntroduction(@RequestBody EmployeeProfileDTO employeeProfileDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        EmployeeProfileDTO updatedEmployeeProfile = employeeProfileService.updateEmployeeIntroduction(username, employeeProfileDTO);
        if (updatedEmployeeProfile != null) {
            return ResponseEntity.ok(updatedEmployeeProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{empId}")
//    public ResponseEntity<?> uploadProfilePicture(@PathVariable String empId, @RequestParam("file") MultipartFile file) {
//        try {
//
//            String updatedProfile = employeeProfileService.uploadProfilePicture(empId, file);
//           // String uploadedFilePath = employeeProfileService.uploadProfilePicture(id, file);
//            return ResponseEntity.ok(updatedProfile);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 업로드할 수 없습니다." + e.getMessage());
//        }
//    }

}

