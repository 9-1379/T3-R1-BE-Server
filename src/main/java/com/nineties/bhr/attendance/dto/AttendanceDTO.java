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

    private boolean late; // 지각 여부를 나타내는 필드

    private boolean absent; // 결근 여부를 나타내는 필드

    private int attendanceCount; // 출석 횟수

    private int lateCount; // 지각 횟수

    private int absenceCount; // 결석 횟수
}
