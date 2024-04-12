package com.nineties.bhr.attendance.dto;

import com.nineties.bhr.attendance.domain.AttendanceStatus;
import lombok.Data;
import java.util.Date;

@Data
public class AttendanceListDTO {
    Date startDate; // 날짜
    String empName; // 이름
    Long empNo; // 사번
    String deptName; // 부서
    String jobId; // 직위
    Date timeIn; // 출근 시간
    Date timeOut; // 퇴근 시간
    AttendanceStatus status; // 상태
}
