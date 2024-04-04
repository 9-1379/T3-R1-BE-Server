package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emp/dashboard")
public class EmployeeProfileController {

    @Autowired
    private EmployeeProfileService employeeProfileService;

    @GetMapping("/{Id}")
    public ResponseEntity<EmployeeProfileDTO> getEmployeeById(@PathVariable("id") String id) {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
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

////    @PostMapping("/{id}")
//    public ResponseEntity<?> uploadProfilePicture(@PathVariable String employeeId, @RequestParam("file") MultipartFile file) {
//        try {
//            EmployeeProfileDTO updatedProfile = employeeProfileService.updateProfilePicture(employeeId, file);
//            return ResponseEntity.ok(updatedProfile); //업데이트된 사용자 프로필을 반환
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 업로드할 수 없습니다." + e.getMessage());
//        }
//    }
//}

