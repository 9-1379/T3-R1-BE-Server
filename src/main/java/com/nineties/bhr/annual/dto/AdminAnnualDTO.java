package com.nineties.bhr.annual.dto;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.emp.domain.Employees;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAnnualDTO {

    private String annualYear;
    private Long annualTotal;
}
