package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    public EmployeeProfileDTO uploadProfilePicture(String id, MultipartFile file) {
        try {
            Employees employee = employeesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            //파일 저장 로직
//        String fileName = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);
//        Path uploadPath = Paths.get("uploads/");

            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String uploadDir = "uploads/";
                String savePath = uploadDir + storedFileName;

                Path uploadPath = Paths.get(savePath);
                try {
                    System.out.println("Creating directories: " + uploadPath.getParent());
                    Files.createDirectories(uploadPath.getParent());
                    System.out.println("Copying file: " + uploadPath);
                    Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Failed to save file: " + e.getMessage());
                    throw new RuntimeException("파일을 저장하는 데 실패했습니다.", e);
                }

                //파일 경로를 데이터베이스에 저장
                String fullPath = "/" + savePath;
                employee.setProfilePicture(fullPath);
                employeesRepository.save(employee);

                return convertEntityToDTO(employee);
            } else {
                throw new IOException("File is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는 데 실패했습니다.", e);
        }
    }

        private EmployeeProfileDTO convertEntityToDTO (Employees employee) {
            EmployeeProfileDTO dto = new EmployeeProfileDTO();
            dto.setId(employee.getId());
            dto.setName(employee.getName());
            dto.setDeptName(employee.getDept().getDeptName()); // dept가 Employee 엔티티에 연결되어 있다고 가정합니다.
            dto.setPosition(employee.getPosition());
            dto.setIntroduction(employee.getIntroduction());

            String profilePicturePath = employee.getProfilePicture();
            if (profilePicturePath != null) {
                String serverUrl = "http://localhost:8000";
                dto.setProfilePicture(serverUrl + profilePicturePath);
            } else {
                dto.setProfilePicture(null);
            }

            return dto;
        }
     }

