package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.dto.EmployeeProfileProjection;
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

    // 파일 저장 외부 디렉토리 지정 (시스템의 루트 디렉토리 아래에 위치)
    private final String UPLOAD_DIR = "/home/joo/finalProject/T3-R2-FE-Server/src/assets/";

    public EmployeeProfileProjection getEmployeeByUsername(String username) {
        return employeesRepository.findEmpProfile(username);
    }

    public EmployeeProfileDTO updateEmployeeIntroduction(String username, String introduction) {
        Employees employee = employeesRepository.findByUsername(username);
        employee.setIntroduction(introduction);
        employee = employeesRepository.save(employee);
        return convertEntityToDTO(employee);
    }

    // 프로필 사진 업로드 메서드
    public String uploadProfilePicture(String empId, MultipartFile file) throws IOException {
        Employees employee = employeesRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!file.isEmpty()) {
            // 기존 프로필 사진 삭제
            deleteProfilePicture(empId);

            // 새로운 프로필 사진 업로드
            String originalFilename = file.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            String savePath = UPLOAD_DIR + storedFileName;

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 파일 경로를 데이터베이스에 저장 (저장된 파일 경로)
            String relativePath = storedFileName;
            employee.setProfilePicture(relativePath);
            employeesRepository.save(employee);

            return relativePath;
        } else {
            throw new RuntimeException("File is empty");
        }
    }

    // 기존 프로필 사진 삭제 메서드
    public void deleteProfilePicture(String empId) {
        Employees employee = employeesRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String oldProfilePicture = employee.getProfilePicture();
        if (oldProfilePicture != null) {
            // 기존 프로필 사진 파일 삭제
            Path oldFilePath = Paths.get(UPLOAD_DIR + oldProfilePicture);
            try {
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                throw new RuntimeException("기존 프로필 사진 파일 삭제 실패: " + e.getMessage());
            }
        }
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