package com.nineties.bhr.attendance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AttendanceDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date timeIn;

    @JsonFormat(pattern = "HH:mm:ss")
    private Date timeOut;

    private String employeeId;
}
