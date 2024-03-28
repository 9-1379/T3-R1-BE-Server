package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class EmployeesProfileService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesProfileService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employees getEmployeeById(String id) {
        return employeesRepository.findById(id).orElse(null);
    }

    public Employees updateEmployee(String id, Employees employeeDetails) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

        employee.setName(employeeDetails.getName());
        employee.setDept(employeeDetails.getDept());
        employee.setPosition(employeeDetails.getPosition());
        employee.setIntroduction(employeeDetails.getIntroduction());

        return employeesRepository.save(employee);
    }

    public boolean deleteIntroduction(String id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

        employee.setIntroduction(null);
        employeesRepository.save(employee);
        return true;
    }

    public void uploadProfilePicture(String id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (filename.contains("..")) {
            throw new RuntimeException("Cannot store file with relative path outside current directory " + filename);
        }

        String uploadDir = "upload-dir"; // 파일을 저장할 디렉토리
        Path uploadPath = Paths.get(uploadDir);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 데이터베이스에 파일 경로 업데이트
            Employees employee = employeesRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));
            employee.setProfilePicture(filePath.toString());
            employeesRepository.save(employee);
        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + filename + ". Please try again!", e);
        }
    }
}
