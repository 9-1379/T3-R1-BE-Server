package com.nineties.bhr.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class AttendanceDTO {
    private Long empNo;
    private Long id;
    private Date timeIn;
}
