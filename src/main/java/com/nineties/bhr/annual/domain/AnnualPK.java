package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AnnualPK {

    private String annualYear;

    private Employees employees;

}
