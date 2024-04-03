package com.nineties.bhr.attendance.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AttendanceDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Date timeIn;
    private Date timeOut;
    private Long employeeId;
    private String type; // 출근(IN) 또는 퇴근(OUT)을 나타내는 문자열 필드

    public AttendanceDTO() {
    }

    public AttendanceDTO(Long id, Date startDate, Date endDate, Date timeIn, Date timeOut, Long employeeId, String type) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.employeeId = employeeId;
        this.type = type;
    }
}
