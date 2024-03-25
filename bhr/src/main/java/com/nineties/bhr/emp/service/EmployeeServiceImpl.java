package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import com.nineties.bhr.emp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.nineties.bhr.emp.domain.Status;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Override
    public List<Employees> findAllEmployees() {
        return employeesRepository.findAll();
    }

//    @Override
//    public void retireEmployee(String employeeId) {
//        Employees employee = employeesRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        employee.setStatus(Status.LEAVE); // 직원 상태를 LEAVE로 변경
//        employeesRepository.save(employee);
//    }
}