package com.nineties.bhr.attendance.controller;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.domain.AttendanceStatus;
import com.nineties.bhr.attendance.dto.AttendanceListDTO;
import com.nineties.bhr.attendance.dto.AttendanceStatusDTO;
import com.nineties.bhr.attendance.service.AdminAttendanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
public class AdminAttendanceController {

    private final AdminAttendanceService adminAttendanceService;

    public AdminAttendanceController(AdminAttendanceService adminAttendanceService) {
        this.adminAttendanceService = adminAttendanceService;
    }

    @GetMapping("/summary")
    public AttendanceStatusDTO getAttendanceStatus() {
        return adminAttendanceService.calculateAttendanceStatus();
    }

    @GetMapping("/list")
    public List<Attendance> getAttendanceList(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "status", required = false) AttendanceStatus status) {
        return adminAttendanceService.getAttendanceList(name, date, status);
    }
}
