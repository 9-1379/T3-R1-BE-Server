package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.repository.AdminAnnualRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAnnualService {

    private final AdminAnnualRepository adminAnnualRepository;
    private final EmployeesRepository employeesRepository;

    @Autowired
    public AdminAnnualService(AdminAnnualRepository adminAnnualRepository, EmployeesRepository employeesRepository) {
        this.adminAnnualRepository = adminAnnualRepository;
        this.employeesRepository = employeesRepository;
    }

    public void newTotalAnnual(AdminAnnualDTO adminAnnualDTO) {

        List<Employees> employeesList = employeesRepository.findAll();

        for (int i = 0; i < employeesList.size(); i++) {
            Employees employees = employeesList.get(i);
            Annual annual = new Annual();
            annual.setAnnualYear(adminAnnualDTO.getAnnualYear());
            annual.setEmployees(employees);
            annual.setAnnualTotal(adminAnnualDTO.getAnnualTotal());
            annual.setAnnualUsed(0L);
            adminAnnualRepository.save(annual);
        }
    }
}
