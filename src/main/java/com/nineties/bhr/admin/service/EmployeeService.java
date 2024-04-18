package com.nineties.bhr.admin.service;

import com.nineties.bhr.admin.dto.EmployeeDTO;
import com.nineties.bhr.emp.domain.*;
import com.nineties.bhr.emp.repository.DeptRepository;
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
    private DeptRepository deptRepository;

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
        if (updatedEmployee.getAddress() != null) {
            Address updatedAddress = updatedEmployee.getAddress();
            if (employee.getAddress() == null) {
                employee.setAddress(new Address());
            }
            employee.getAddress().setPostcode(updatedAddress.getPostcode());
            employee.getAddress().setAddress(updatedAddress.getAddress());
            employee.getAddress().setDetailAddress(updatedAddress.getDetailAddress());
            employee.getAddress().setExtraAddress(updatedAddress.getExtraAddress());
        } else {
            // 주소가 없는 경우 기존 주소를 null로 설정
            employee.setAddress(null);
        }


        // 부서 업데이트
        String deptName = updatedEmployee.getDeptName();
        if (deptName != null) {
            // 새로운 부서를 찾거나 생성합니다.
            Dept dept = deptRepository.findByDeptName(deptName);
            if (dept == null) {
                // 부서가 없으면 새로운 부서를 생성합니다.
                dept = new Dept();
                dept.setDeptName(deptName);
            }
            // 직원의 부서를 업데이트합니다.
            employee.setDept(dept);
        }

        // 권한 및 부서 업데이트
        // 추가적으로 필요한 필드 업데이트
        // 주의: 모든 필드를 업데이트하는 방법에 대해 고려해야 함
        employeesRepository.save(employee);
        return convertToDTO(employee);
    }

}