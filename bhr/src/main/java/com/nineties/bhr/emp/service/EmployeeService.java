package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.dto.EmployeeDTO;
import com.nineties.bhr.emp.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAllEmployees();
    EmployeeDTO getEmployeeById(String id);
    void retireEmployee(String employeeId);

    void retireMultipleEmployees(List<String> employeeIds);

}
