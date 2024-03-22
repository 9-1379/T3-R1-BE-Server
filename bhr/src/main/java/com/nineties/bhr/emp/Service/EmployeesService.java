package com.nineties.bhr.emp.Service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public Employees getEmployeesById(Long id) {
        return (Employees) employeesRepository.findById(id).orElse(null);
    }

    public Employees updateEmployees(Employees employees) {
        return employeesRepository.save(employees);
    }
}
