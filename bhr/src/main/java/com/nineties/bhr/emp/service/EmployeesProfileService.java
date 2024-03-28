package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmployeesProfileService {

private final EmployeesRepository employeesRepository;

@Autowired
public EmployeesProfileService (EmployeesRepository employeesRepository) {
    this.employeesRepository = employeesRepository;
}

public Employees getEmployeeById(String id) {
    return employeesRepository.findById(id).orElse(null);
}

public Employees updateEmployee(String id, Employees employeeDetails) {
    Employees employee = employeesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

    employee.setName(employeeDetails.getName());
    employee.setDept(employeeDetails.getDept());
    employee.setPosition(employeeDetails.getPosition());
    employee.setIntroduction(employeeDetails.getIntroduction());

    return employeesRepository.save(employee);
}

public boolean deleteIntroduction(String id) {
    Employees employee = employeesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found for this id :: " + id));

    employee.setIntroduction(null);
    employeesRepository.save(employee);
    return true;

    }

}

