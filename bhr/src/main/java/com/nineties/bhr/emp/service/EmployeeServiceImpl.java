package com.nineties.bhr.emp.service;
import com.nineties.bhr.emp.domain.Status;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeDTO;
import com.nineties.bhr.emp.dto.EmployeeDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(String id) {
        return employeesRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
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
        if (employee.getDept() != null) {
            dto.setDeptName(employee.getDept().getDeptName());
        }
        // 추가적으로 필요한 필드를 여기서 설정
        return dto;
    }

    @Override
    public void retireMultipleEmployees(List<String> employeeIds) {
        List<Employees> employeesToRetire = employeesRepository.findAllById(employeeIds);
        employeesToRetire.forEach(employee -> employee.setStatus(Status.LEAVE));
        employeesRepository.saveAll(employeesToRetire);
    }

}
