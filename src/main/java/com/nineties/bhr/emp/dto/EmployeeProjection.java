package com.nineties.bhr.emp.dto;

import lombok.Getter;
import lombok.Setter;

public interface EmployeeProjection {
    String getId();
    String getEmpNo();
    String getEmail();
    String getName();
    String getDeptName();
    String getPosition();
    String getIntroduction();
    String getProfilePicture();
}