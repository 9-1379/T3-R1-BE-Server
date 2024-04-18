package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import lombok.Data;

import java.io.Serializable;

@Data
public class AnnualPK implements Serializable {

    private String annualYear;

    private Employees employees;

}
