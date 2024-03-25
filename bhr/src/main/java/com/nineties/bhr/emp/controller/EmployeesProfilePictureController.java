package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.Service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
public class EmployeesProfilePictureController {

    private final EmployeesService employeesService;

    @Autowired
    public EmployeesProfilePictureController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @PostMapping("/api/employees/{id}/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // 파일 업로드 로직 호출. 예를 들어, employeesService.uploadProfilePicture(id, file);

            String message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.ok().body(message);
        } catch (Exception e) {
            String message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(500).body(message);
        }
    }
}
