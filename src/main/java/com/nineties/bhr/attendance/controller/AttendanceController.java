package com.nineties.bhr.attendance.controller;

import com.nineties.bhr.attendance.dto.AttendanceDTO;
import com.nineties.bhr.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class
AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/startWork")
    public AttendanceDTO startWork(@RequestParam String employeeId) {
        return attendanceService.recordStartWork(employeeId);
    }

    @PostMapping("/endWork")
    public AttendanceDTO endWork(@RequestParam String employeeId) {
        return attendanceService.recordEndWork(employeeId);
    }

    @GetMapping("/record/{employeeId}")
    public AttendanceDTO getAttendanceRecord(@PathVariable String employeeId) {
        return attendanceService.getAttendanceRecord(employeeId);
    }

    @GetMapping("/monthlySummary/{employeeId}")
    public Map<String, Integer> getMonthlyAttendanceSummary(@PathVariable String employeeId) {
        return attendanceService.getMonthlyAttendanceSummary(employeeId);
    }
}
