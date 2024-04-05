package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/emp/dashboard")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @Autowired
    public EmployeeProfileController(EmployeeProfileService employeeProfileService) {
        this.employeeProfileService = employeeProfileService;
    }

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

    @PostMapping("/{id}")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String uploadedFilePath = employeeProfileService.uploadProfilePicture(id, file);
            return ResponseEntity.ok("파일이 성공적으로 업로드되었습니다: " + uploadedFilePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 업로드할 수 없습니다." + e.getMessage());
        }
    }

}

