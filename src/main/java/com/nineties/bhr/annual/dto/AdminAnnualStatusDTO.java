package com.nineties.bhr.annual.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAnnualStatusDTO {
    private String name;
    private Long annualTotal;
    private Long annualCnt;
    private Long annualUsed;
    private String  deptName;
}
