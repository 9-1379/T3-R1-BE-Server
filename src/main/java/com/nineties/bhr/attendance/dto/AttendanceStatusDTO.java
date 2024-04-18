package com.nineties.bhr.attendance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceStatusDTO {
    private int total; // 전체 직원 수
    private int presentCount; // 출근
    private int lateCount; // 지각
    private int onLeaveCount; // 휴가
}
