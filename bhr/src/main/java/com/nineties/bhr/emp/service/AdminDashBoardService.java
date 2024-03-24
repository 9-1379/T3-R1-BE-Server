package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashBoardService {
    private final EmployeesRepository employeesRepository;


    @Autowired
    public AdminDashBoardService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Long getEmployeeCount() {
        return employeesRepository.countBy();
    }

    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }
}
