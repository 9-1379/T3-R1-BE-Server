package com.nineties.bhr.admin.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttendanceCountDto  {

    private Long id;
    private Long absenteesCount;
    private Long totalEmployee;
    private Long earlyBirdsCount;
}
