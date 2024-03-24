package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;

import java.util.List;

public interface EmployeeService {
    List<Employees> findAllEmployees();
}

