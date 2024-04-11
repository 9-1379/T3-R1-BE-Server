package com.nineties.bhr.admin.service;

import com.nineties.bhr.admin.dto.AttendanceStatusDTO;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class DashboardService {

    private final AttendanceRepository attendanceRepository;
    private final AnnualListRepository annualListRepository;
    private final EmployeesRepository employeesRepository;

    public DashboardService(AttendanceRepository attendanceRepository, AnnualListRepository annualListRepository, EmployeesRepository employeesRepository) {
        this.attendanceRepository = attendanceRepository;
        this.annualListRepository = annualListRepository;
        this.employeesRepository = employeesRepository;
    }
    // 기타 필요한 Repository 주입

    public AttendanceStatusDTO calculateAttendanceStatus() {

        AttendanceStatusDTO dto = new AttendanceStatusDTO();

        LocalDate todayLocalDate = LocalDate.now();
        LocalTime nineAM = LocalTime.of(9, 0,1);

        // LocalDate + LocalTime -> Date
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nineAmDate = Date.from(todayLocalDate.atTime(nineAM).atZone(ZoneId.systemDefault()).toInstant());

        // 전체 직원 수
        long totalEmployees = employeesRepository.count();

        // 출근자
        int presentCount = attendanceRepository.countPresentBeforeNine(today, nineAmDate);
        dto.setPresentCount(presentCount);

        // 지각자
        int lateCount = attendanceRepository.countLateAfterNine(today, nineAmDate);
        dto.setLateCount(lateCount);

        // 휴가자
        int onLeaveCount = annualListRepository.countByDateWithinAnnualLeave(today);
        dto.setOnLeaveCount(onLeaveCount);

        // 미출근자
        int absentCount = (int) (totalEmployees - (presentCount + lateCount + onLeaveCount));
        dto.setAbsentCount(absentCount);

        return dto;
    }
}
