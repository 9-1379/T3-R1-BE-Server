package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileDTO;
import com.nineties.bhr.emp.dto.EmployeeProfileProjection;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeProfileService {

    @Autowired
    private EmployeesRepository employeesRepository;

    public EmployeeProfileDTO empProfile (String username) {

        Employees employees = employeesRepository.findByUsername(username);

        EmployeeProfileDTO empProfileDTO = new EmployeeProfileDTO();
        empProfileDTO.setName(employees.getName());
        empProfileDTO.setPosition(employees.getPosition());
        empProfileDTO.setDeptName(employees.getDept().getDeptName());
        empProfileDTO.setJobId(employees.getJobId());
        empProfileDTO.setHireDate(employees.getHireDate());
        empProfileDTO.setIntroduction(employees.getIntroduction());

        return empProfileDTO;
    }


    public void updateEmployeeIntroduction(String username, EmployeeProfileDTO employeeProfileDTO) {

        Employees employee = employeesRepository.findByUsername(username);

        employee.setIntroduction(employeeProfileDTO.getIntroduction());
        employeesRepository.save(employee);
    }
}

