package com.nineties.bhr.admin.service;

import com.nineties.bhr.emp.domain.*;
import com.nineties.bhr.admin.dto.EmployeeDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import jakarta.persistence.Embedded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Embedded
    private Address address;

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<EmployeeDTO> findAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(String id) {
        return employeesRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void retireEmployee(String employeeId) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setStatus(Status.LEAVE);
        employeesRepository.save(employee);
    }

    private EmployeeDTO convertToDTO(Employees employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setEmpNo(employee.getEmpNo());
        dto.setName(employee.getName());
        dto.setGender(employee.getGender().name());
        dto.setBirthday(employee.getBirthday());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmail(employee.getEmail());
        dto.setPosition(employee.getPosition());
        dto.setJobId(employee.getJobId());
        dto.setHireDate(employee.getHireDate());
        dto.setUsername(employee.getUsername());
        dto.setStatus(employee.getStatus().name());
        dto.setAuthorization(employee.getAuthorization().name());
        dto.setIntroduction(employee.getIntroduction());
        if (employee.getAddress() != null) { // 주소 정보 추가
            dto.setAddress(employee.getAddress());
        }
        if (employee.getDept() != null) {
            dto.setDeptName(employee.getDept().getDeptName());
        }
        // 추가적으로 필요한 필드를 여기서 설정
        return dto;
    }

    public void retireMultipleEmployees(List<String> employeeIds) {
        List<Employees> employeesToRetire = employeesRepository.findAllById(employeeIds);
        employeesToRetire.forEach(employee -> employee.setStatus(Status.LEAVE));
        employeesRepository.saveAll(employeesToRetire);
    }

    public EmployeeDTO updateEmployee(String id, EmployeeDTO updatedEmployee) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setName(updatedEmployee.getName());
        employee.setGender(Gender.valueOf(updatedEmployee.getGender()));
        employee.setBirthday(updatedEmployee.getBirthday());
        employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPosition(updatedEmployee.getPosition());
        employee.setJobId(updatedEmployee.getJobId());
        employee.setHireDate(updatedEmployee.getHireDate());
        employee.setStatus(Status.valueOf(updatedEmployee.getStatus())); // Status 업데이트
        employee.setAuthorization(Role.valueOf(updatedEmployee.getAuthorization())); // Authorization 업데이트
        employee.setIntroduction(updatedEmployee.getIntroduction()); // Introduction 업데이트

        // 주소 업데이트
        Address updatedAddress = updatedEmployee.getAddress();
        if (updatedAddress != null) {
            employee.setAddress(updatedAddress);
        }

        // 부서 업데이트
        String deptName = updatedEmployee.getDeptName();
        if (deptName != null) {
            // 부서를 찾기 위해 직접 해당 직원의 부서를 조회합니다.
            // 이 경우에는 부서가 하나만 존재한다고 가정합니다.
            Dept dept = employee.getDept();
            if (dept == null) {
                throw new RuntimeException("Department not found for employee: " + id);
            }
            // 부서명을 업데이트합니다.
            dept.setDeptName(deptName);
        }

        // 권한 및 부서 업데이트
        // 추가적으로 필요한 필드 업데이트
        // 주의: 모든 필드를 업데이트하는 방법에 대해 고려해야 함
        employeesRepository.save(employee);
        return convertToDTO(employee);
    }

}