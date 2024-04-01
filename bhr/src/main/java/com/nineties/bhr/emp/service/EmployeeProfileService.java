package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeProfileService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeeProfileService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public EmployeeDTO getEmployeeById(String id) {
        return employeesRepository.findById(id).map(this::convertToDto).orElse(null);
    }

    @Transactional
    public EmployeeDTO updateEmployee(String id, EmployeeDTO employeeDetailsDTO) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

        // DTO로부터 엔티티의 데이터를 수동으로 업데이트
        updateEmployeeFromDto(employeeDetailsDTO, employee);

        Employees updatedEmployee = employeesRepository.save(employee);

        return convertToDto(updatedEmployee);
    }

    public boolean deleteIntroduction(String id) {
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

        employee.setIntroduction(null);
        employeesRepository.save(employee);
        return true;
    }

    private EmployeeDTO convertToDto(Employees employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setDeptName(employee.getDept().getDeptName()); // 예시, 실제 구현에 따라 다를 수 있음
        // 필요한 다른 필드도 이와 같은 방식으로 설정
        return dto;
    }

    private void updateEmployeeFromDto(EmployeeDTO dto, Employees employee) {
        employee.setName(dto.getName());
        employee.setPosition(dto.getPosition());
        // Department 등 다른 필드도 필요에 따라 업데이트
        // DTO의 department 정보를 바탕으로 Department 엔티티를 조회하고 설정하는 로직 추가 가능
        // employee.setIntroduction(dto.getIntroduction()); // 필요하다면 소개도 업데이트
    }
}
