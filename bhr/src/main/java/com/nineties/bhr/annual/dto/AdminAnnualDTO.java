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
    private Long annualUsed;
    private String empId;

    public Annual toEntity(Employees employees){
        Annual annual = new Annual();
        annual.setAnnualYear(this.annualYear);
        annual.setAnnualTotal(this.annualTotal);
        annual.setAnnualUsed(this.annualUsed);
        annual.setEmployees(employees);
        return annual;
    }
}
