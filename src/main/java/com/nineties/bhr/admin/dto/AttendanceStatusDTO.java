package com.nineties.bhr.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceStatusDTO {
    private int presentCount;
    private int lateCount;
    private int absentCount;
    private int onLeaveCount;
}
