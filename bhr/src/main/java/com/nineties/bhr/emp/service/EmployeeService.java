package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;

import java.util.List;

public interface EmployeeService {
    List<Employees> findAllEmployees();
    Employees getEmployeeById(String id);
//    void retireEmployee(String employeeId); // 퇴직 처리 메서드 추가
}

