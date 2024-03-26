package com.nineties.bhr.admin.controller;

import com.nineties.bhr.admin.dto.AdminDTO;
import com.nineties.bhr.admin.service.AdminDashBoardService;
import com.nineties.bhr.admin.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class AdminDashBoardController {
    private final AdminDashBoardService adminDashBoardService;
    private final AttendanceService attendanceService;

    @Autowired
    public AdminDashBoardController(AdminDashBoardService adminDashBoardService, AttendanceService attendanceService) {
        this.adminDashBoardService = adminDashBoardService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("/list")
    public Map<String, Object> getEmployees() {
        Map<String, Object> result = new HashMap<>();
        List<AdminDTO> employeeList = adminDashBoardService.getAllEmployees();
        Long count = adminDashBoardService.getCount();

        result.put("employeeList", employeeList);
        result.put("count", count);

        return result;
    }

    //    @GetMapping("/counts")
//    public ResponseEntity<AttendanceCountDto> getAttendanceCounts() {
//        Long earlyBirdsCount = attendanceService.countEarlyBirds();
////        Long absenteesCount = attendanceService.countAbsentees();
//
//        AttendanceCountDto attendanceCountDto = new AttendanceCountDto();
//
//        return ResponseEntity.ok(attendanceCountDto);
//    }
//
//    @GetMapping("/absenteesCount")
//    public ResponseEntity<Long> countAbsentees() {
//        Long earlyBirdsCount = attendanceService.countEarlyBirds();
//        Long absenteesCount = attendanceService.countAbsentees();
//        return ResponseEntity.ok(absenteesCount);
//    }
    @GetMapping("/count")
    public ResponseEntity<Long> countEarlyEmp() {
        Long earlyEmp = attendanceService.countEarlyEmployees();
        return ResponseEntity.ok(earlyEmp);
    }
}

