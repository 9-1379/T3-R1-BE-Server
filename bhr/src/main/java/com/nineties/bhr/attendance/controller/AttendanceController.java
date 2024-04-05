package com.nineties.bhr.attendance.controller;

import com.nineties.bhr.attendance.dto.AttendanceDTO;
import com.nineties.bhr.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/startWork")
    public AttendanceDTO startWork(@RequestParam String employeeId) {
        return attendanceService.recordStartWork(employeeId);
    }

    // attendanceId 대신 employeeId를 사용하여 퇴근 기록을 처리하는 메서드로 수정
    @PostMapping("/endWork")
    public AttendanceDTO endWork(@RequestParam String employeeId) {
        // 서비스 메서드 호출을 변경된 파라미터에 맞게 수정
        return attendanceService.recordEndWork(employeeId);
    }

    @GetMapping("/record/{employeeId}")
    public AttendanceDTO getAttendanceRecord(@PathVariable String employeeId) {
        return attendanceService.getAttendanceRecord(employeeId);
    }
}
