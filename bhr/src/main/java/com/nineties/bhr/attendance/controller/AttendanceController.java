package com.nineties.bhr.attendance.controller;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/startWork")
    public Attendance startWork(@RequestParam Long employeeId) {
        return attendanceService.recordStartWork(employeeId);
    }

    @PostMapping("/endWork")
    public Attendance endWork(@RequestParam Long attendanceId) {
        return attendanceService.recordEndWork(attendanceId);
    }
}
