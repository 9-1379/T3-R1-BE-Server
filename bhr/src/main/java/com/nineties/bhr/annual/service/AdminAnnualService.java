package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.repository.AdminAnnualRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminAnnualService {

    private final AdminAnnualRepository adminAnnualRepository;
    private final EmployeesRepository employeesRepository;

    @Autowired
    public AdminAnnualService(AdminAnnualRepository adminAnnualRepository, EmployeesRepository employeesRepository) {
        this.adminAnnualRepository = adminAnnualRepository;
        this.employeesRepository = employeesRepository;
    }

    public void newTotalAnnual(AdminAnnualDTO adminAnnualDTO, String username) {
        String empId =


    }
}
