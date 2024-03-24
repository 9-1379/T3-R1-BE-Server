package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.AdminDTO;
import com.nineties.bhr.emp.mapper.EmployeeMapper;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDashBoardService {
    private final EmployeesRepository employeesRepository;
    private final EmployeeMapper employeeMapper;


    @Autowired
    public AdminDashBoardService(EmployeesRepository employeesRepository, EmployeeMapper employeeMapper) {
        this.employeesRepository = employeesRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<AdminDTO> getAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(employeeMapper::adminDTO)
                .collect(Collectors.toList());
    }
    public Long getCount() {
        return employeesRepository.countBy();
    }
}

