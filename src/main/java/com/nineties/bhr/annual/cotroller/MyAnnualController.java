package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.repository.MyAnnualRepository;
import com.nineties.bhr.annual.service.MyAnnualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/myAnnual")
public class MyAnnualController {

    private final MyAnnualService myAnnualService;

    @Autowired
    public MyAnnualController(MyAnnualService myAnnualService) {
        this.myAnnualService = myAnnualService;
    }

    @GetMapping("/{annualYear}/{empId}")
    public Map<String, Object> getMyAnnualDetailsByYearAndEmpId(@PathVariable String annualYear, @PathVariable String empId) {
        return myAnnualService.getMyAnnualDetailsByYearAndEmpId(annualYear, empId);
    }
}
