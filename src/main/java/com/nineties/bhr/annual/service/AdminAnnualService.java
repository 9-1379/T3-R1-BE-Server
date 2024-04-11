package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.dto.AdminAnnualStatusDTO;
import com.nineties.bhr.annual.repository.AdminAnnualRepository;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminAnnualService {

    private final AdminAnnualRepository adminAnnualRepository;
    private final EmployeesRepository employeesRepository;
    private final AnnualListRepository annualListRepository;

    @Autowired
    public AdminAnnualService(AdminAnnualRepository adminAnnualRepository, EmployeesRepository employeesRepository, AnnualListRepository annualListRepository) {
        this.adminAnnualRepository = adminAnnualRepository;
        this.employeesRepository = employeesRepository;
        this.annualListRepository = annualListRepository;
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

    public List<AdminAnnualStatusDTO> empStatusAll(String annualYear) {

        List<Employees> employeesList = employeesRepository.findAll();

        List<AdminAnnualStatusDTO> statusDTOList = new ArrayList<>();

        for (int i = 0; i < employeesList.size(); i++){
            Employees employees = employeesList.get(i);
            System.out.println(employees.getEmpNo());
            Annual annualIF = adminAnnualRepository.findByEmployeesAndAnnualYear(employees, annualYear);
            Long cnt = annualListRepository.findAnnualCountByEmployeeAndYear(employees.getId(), annualYear);

            AdminAnnualStatusDTO statusDTO = new AdminAnnualStatusDTO();

            statusDTO.setName(employees.getName());
            statusDTO.setAnnualTotal(annualIF.getAnnualTotal());
            statusDTO.setAnnualCnt(cnt);
            statusDTO.setAnnualUsed(annualIF.getAnnualUsed());

            statusDTOList.add(statusDTO);
        }
       return statusDTOList;
    }
}
