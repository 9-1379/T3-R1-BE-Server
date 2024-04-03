package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private EmployeeProfileDTO convertEntityToDTO(Employees employee) {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDeptName(employee.getDept().getDeptName()); // dept가 Employee 엔티티에 연결되어 있다고 가정합니다.
        dto.setPosition(employee.getPosition());
        dto.setIntroduction(employee.getIntroduction());
        return dto;
    }

    private final Path rootLocation = Paths.get("upload-dir");

    public String uploadProfilePicture(String id, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("파일을 업로드할 수 없습니다. 파일이 비어 있습니다.");

            }

            Path destinationFile = this.rootLocation.resolve(Paths.get(id))
                    .normalize().toAbsolutePath().resolve(file.getOriginalFilename());

            if (!destinationFile.getParent().toFile().exists()) {
                Files.createDirectories(destinationFile.getParent());
            }

            Files.copy(file.getInputStream(), destinationFile);

            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("파일을 저장하는데 실패했습니다.", e);

             }

        }

    }