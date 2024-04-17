package com.nineties.bhr.emp.dto;

import lombok.Getter;
import lombok.Setter;

public interface EmployeeProjection {
    Long getEmpNo();

    String getName();

    String getEmail();

    String getDeptName();

    String getPosition();

    String getJobId();

    String getIntroduction();
}