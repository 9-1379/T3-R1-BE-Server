package com.nineties.bhr.emp.controller;

import com.nineties.bhr.emp.Service.EmployeesService;
import com.nineties.bhr.emp.domain.Employees;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    private final EmployeesService employeesService;

    @Autowired
    public EmployeesController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping("/{id}")
    public Employees getEmployeeById(@PathVariable Long id) {
        return employeesService.getEmployeesById(id);
    }

    @PutMapping("/{id}")
    public Employees updateEmployees(@PathVariable Long id, @RequestBody Employees employees) {
            employees.setId(id);
            return employeesService.updateEmployees(employees);

    }

}
