package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.stereotype.Service;

@Service
public class AnnualListService {
    private final AnnualListRepository annualListRepository;

    private final EmployeesRepository employeesRepository;

    public AnnualListService(AnnualListRepository annualListRepository, EmployeesRepository employeesRepository) {
        this.annualListRepository = annualListRepository;
        this.employeesRepository = employeesRepository;
    }

    public void annualList(AnnualListDTO annualListDTO, String username) {
        AnnualList list = new AnnualList();
        list.setAnnualYear(annualListDTO.getAnnualYear());
        list.setStartDate(annualListDTO.getStartDate());
        list.setEndDate(annualListDTO.getEndDate());

        Employees employees = new Employees();
        employees = employeesRepository.findByUsername(username);
        list.setEmployees(employees);

        list.setAnnualCnt(); // working
         annualListRepository.save(list);
    }
}
