package com.nineties.bhr.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceStatusDTO {
    private int presentCount; // 출근
    private int leaveCount; // 퇴근
    private int lateCount; // 지각
    private int absentCount; // 결근
    private int onLeaveCount; // 휴가
}
