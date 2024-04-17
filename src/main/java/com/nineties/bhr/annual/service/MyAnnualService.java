package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.annual.repository.MyAnnualRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MyAnnualService {

    private final MyAnnualRepository myAnnualRepository;
    private final AnnualListRepository annualListRepository;
    private final EmployeesRepository employeesRepository;

    @Autowired
    public MyAnnualService(MyAnnualRepository myAnnualRepository, AnnualListRepository annualListRepository, EmployeesRepository employeesRepository) {
        this.myAnnualRepository = myAnnualRepository;
        this.annualListRepository = annualListRepository;
        this.employeesRepository = employeesRepository;
    }

    public Map<String, Object> getMyAnnualDetailsByYearAndEmpId(String annualYear, String empId) {
        Optional<Annual> annualOptional = myAnnualRepository.findByAnnualYearAndEmployees_Id(annualYear, empId);
        Map<String, Object> annualDetails = new HashMap<>();

        Long cnt = annualListRepository.findAnnualCountByEmployeeAndYear(empId, annualYear);

        annualOptional.ifPresent(annual -> {
            annualDetails.put("annualYear", annual.getAnnualYear());
            annualDetails.put("empId", annual.getEmployees().getId());
            annualDetails.put("annualTotal", annual.getAnnualTotal());
            annualDetails.put("annualUsed", cnt);
        });
        return annualDetails;
    }
}
