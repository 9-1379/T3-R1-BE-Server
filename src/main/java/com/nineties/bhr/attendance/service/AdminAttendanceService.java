package com.nineties.bhr.attendance.service;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.dto.AttendanceListDTO;
import com.nineties.bhr.attendance.dto.AttendanceStatusDTO;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminAttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AnnualListRepository annualListRepository;
    private final EmployeesRepository employeesRepository;

    public AdminAttendanceService(AttendanceRepository attendanceRepository, AnnualListRepository annualListRepository, EmployeesRepository employeesRepository) {
        this.attendanceRepository = attendanceRepository;
        this.annualListRepository = annualListRepository;
        this.employeesRepository = employeesRepository;
    }

    // 출근 현황 (출근자, 퇴근자, 지각자, 휴가자, 미출근자)
    public AttendanceStatusDTO calculateAttendanceStatus() {

        AttendanceStatusDTO dto = new AttendanceStatusDTO();

        LocalDate todayLocalDate = LocalDate.now();
        LocalTime nineAM = LocalTime.of(9, 0,1);

        // LocalDate + LocalTime -> Date
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date nineAmDate = Date.from(todayLocalDate.atTime(nineAM).atZone(ZoneId.systemDefault()).toInstant());

        // 전체 직원 수
        long totalEmployees = employeesRepository.countActiveEmployees();

        // 출근자
        int presentCount = attendanceRepository.countPresentBeforeNine(today, nineAmDate);
        dto.setPresentCount(presentCount);

        // 퇴근자
        int leaveCount = attendanceRepository.findTodaysLeavers();

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

    // 출근 현황 리스트
    // status에는 휴가와 결근만 표기
    // 다 null이면 오늘 날짜에 대한 근태 기록
    // status가 not null이면 오늘 날짜 기준으로 해당 상태 검색
    // name, date에 따른 동적 쿼리
    public List<Attendance> getAttendanceList(String name, Date date, String status) {


    }
}
