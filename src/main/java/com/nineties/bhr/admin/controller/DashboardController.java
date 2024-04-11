package com.nineties.bhr.admin.controller;

import com.nineties.bhr.admin.dto.AttendanceStatusDTO;
import com.nineties.bhr.admin.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    public AttendanceStatusDTO getDashboardMetrics() {
        return dashboardService.calculateAttendanceStatus();
    }
}
