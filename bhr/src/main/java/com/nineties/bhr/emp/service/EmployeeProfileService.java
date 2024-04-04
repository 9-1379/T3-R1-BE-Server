package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class EmployeeProfileService {

    @Autowired
    private EmployeesRepository employeesRepository;

    // application.properties에서 설정된 파일 저장 디렉터리 경로를 주입받습니다.
    @Value("${file.upload-dir}")
    private String uploadDir;

    public EmployeeProfileDTO getEmployeeById(String username) {
        return employeesRepository.findByUsernameProfile(username).map(this::convertEntityToDTO).orElse(null);
    }

    public EmployeeProfileDTO updateEmployeeIntroduction(String username, String introduction) {
        Employees employee = employeesRepository.findById(username).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setIntroduction(introduction);
        employee = employeesRepository.save(employee);
        return convertEntityToDTO(employee);
    }

    // 파일을 저장하고, 저장된 파일의 경로를 반환하는 메서드
//    private String storeFile(MultipartFile file) {
//        String uploadDir = "/path/to/uploads"; // 파일 저장 경로 설정
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename()); // 파일 이름 정규화
//
//                Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize();// 파일을 저장할 디렉토리의 경로를 Path 객체로 생성
//                Files.createDirectories(targetLocation); // 디렉토리가 존재하지 않는 경우, 해당 디렉토리와 필요한 모든 상위 디렉토리를 생성
//                Path destinationFile = targetLocation.resolve(fileName); // 최종 파일 저장 경로 생성
//                Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING); // 파일 저장 (기존 파일 존재하는 경우 대체)
//
//            return destinationFile.toString();
//
//    }
    // 데이터베이스에서 프로필 사진 업데이트를 위한 메서드
//    public EmployeeProfileDTO updateProfilePicture(String id, MultipartFile file) {
//        String filePath = storeFile(file); // 파일 저장 및 저장 경로 반환
//
//        Employees employee = employeesRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        employee.setProfilePicture(filePath); // 저장된 파일 경로 설정
//        Employees updatedEmployee = employeesRepository.save(employee);
//
//        return convertEntityToDTO(updatedEmployee); //DTO 변환하여 반환
//    }

    private EmployeeProfileDTO convertEntityToDTO(Employees employee) {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDeptName(employee.getDept().getDeptName());
        dto.setPosition(employee.getPosition());
        dto.setIntroduction(employee.getIntroduction());
        dto.setProfilePicture(employee.getProfilePicture()); // 파일 경로를 포함시키는 부분
        return dto;
    }
}




