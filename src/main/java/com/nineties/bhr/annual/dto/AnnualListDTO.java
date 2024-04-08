package com.nineties.bhr.annual.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AnnualListDTO {

    private Long id;
    private String annualYear;
    private Date startDate;
    private Date endDate;
}
