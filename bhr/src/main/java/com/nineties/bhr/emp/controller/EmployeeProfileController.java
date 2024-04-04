package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/emp/dashboard")
public class EmployeeProfileController {

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @GetMapping
    public ResponseEntity<EmployeeProfileDTO> getEmployeeById() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(name);
        EmployeeProfileDTO employeeProfile = employeeProfileService.getEmployeeById(name);
        if (employeeProfile != null) {
            return ResponseEntity.ok(employeeProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeProfileDTO> updateEmployeeIntroduction(@RequestBody EmployeeProfileDTO employeeProfileDTO) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        EmployeeProfileDTO updatedEmployeeProfile = employeeProfileService.updateEmployeeIntroduction(name, employeeProfileDTO.getIntroduction());
        if (updatedEmployeeProfile != null) {
            return ResponseEntity.ok(updatedEmployeeProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{employeeId}")
//    public ResponseEntity<?> uploadProfilePicture(@PathVariable String employeeId, @RequestParam("file") MultipartFile file) {
//        try {
//            EmployeeProfileDTO updatedProfile = employeeProfileService.updateProfilePicture(employeeId, file);
//            return ResponseEntity.ok(updatedProfile); //업데이트된 사용자 프로필을 반환
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 업로드할 수 없습니다." + e.getMessage());
//        }
//    }
}

