package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class EmployeeProfileService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public EmployeeProfileDTO getEmployeeById(String id) {
        return employeesRepository.findById(id).map(this::convertEntityToDTO).orElse(null);
    }

    public EmployeeProfileDTO updateEmployeeIntroduction(String id, String introduction) {
        Employees employee = employeesRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setIntroduction(introduction);
        employee = employeesRepository.save(employee);
        return convertEntityToDTO(employee);

    }

    public String uploadProfilePicture(String id, MultipartFile file) throws IOException {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        //파일 저장 로직
        String fileName = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString());
        Path uploadPath = Paths.get("uploads/");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        
        //파일 경로를 데이터베이스에 저장
        String relativePath = "/uploads/" + fileName;
        employee.setProfilePicture(relativePath);
        employeesRepository.save(employee);
        
        return relativePath; // 상대 경로 반환
    }

    private EmployeeProfileDTO convertEntityToDTO(Employees employee) {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDeptName(employee.getDept().getDeptName()); // dept가 Employee 엔티티에 연결되어 있다고 가정합니다.
        dto.setPosition(employee.getPosition());
        dto.setIntroduction(employee.getIntroduction());
        dto.setProfilePicture(employee.getProfilePicture()); // 프로필 사진 경로 추가
        return dto;
    }

}
