package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.repository.MyAnnualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MyAnnualService {

    private final MyAnnualRepository myAnnualRepository;

    @Autowired
    public MyAnnualService(MyAnnualRepository myAnnualRepository) {
        this.myAnnualRepository = myAnnualRepository;
    }

    public Map<String, Object> getMyAnnualDetailsByYearAndEmpId(String annualYear, String empId) {
        Optional<Annual> annualOptional = myAnnualRepository.findByAnnualYearAndEmployees_Id(annualYear, empId);
        Map<String, Object> annualDetails = new HashMap<>();
        annualOptional.ifPresent(annual -> {
            annualDetails.put("annualYear", annual.getAnnualYear());
            annualDetails.put("empId", annual.getEmployees().getId());
            annualDetails.put("annualTotal", annual.getAnnualTotal());
            annualDetails.put("annualUsed", annual.getAnnualUsed());
        });
        return annualDetails;
    }
}
