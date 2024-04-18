package com.nineties.bhr.attendance.service;

import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.domain.AttendanceStatus;
import com.nineties.bhr.attendance.dto.AttendanceListDTO;
import com.nineties.bhr.attendance.dto.AttendanceStatusDTO;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.attendance.repository.AttendanceSpecifications;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 출근 현황 (출근자, 퇴근자, 지각자, 휴가자, 미출근자)
     */
    public AttendanceStatusDTO calculateAttendanceStatus() {
        AttendanceStatusDTO dto = new AttendanceStatusDTO();

        LocalDate todayLocalDate = LocalDate.now();

        // LocalDate -> Date
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 전체 직원 수
        long totalEmployees = employeesRepository.countActiveEmployees();
        dto.setTotal((int)totalEmployees);

        // 출근자: 상태가 PRESENT인 직원 수
        int presentCount = attendanceRepository.countByStatusAndStartDate(AttendanceStatus.PRESENT, today);
        dto.setPresentCount(presentCount);

        // 지각자: 상태가 LATE인 직원 수
        int lateCount = attendanceRepository.countByStatusAndStartDate(AttendanceStatus.LATE, today);
        dto.setLateCount(lateCount);

        // 휴가자: 상태가 ON_LEAVE인 직원 수
        int onLeaveCount = attendanceRepository.countByStatusAndStartDate(AttendanceStatus.ON_LEAVE, today);
        dto.setOnLeaveCount(onLeaveCount);

        return dto;
    }


    /**
     * 출근 현황 리스트
     * 다 null이면 오늘 날짜에 대한 근태 기록
     * status가 not null이면 오늘 날짜 기준으로 해당 상태 검색
     * search, date에 따른 동적 쿼리
     */
    public List<AttendanceListDTO> getAttendanceList(String search, Date date, AttendanceStatus status) {

        List<Attendance> results;

        LocalDate todayLocalDate = LocalDate.now();
        Date today = Date.from(todayLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if(search.isBlank() && date == null && status == null) {
            results = attendanceRepository.findByStartDate(today);
        } else if(status == null) {
            Specification<Attendance> spec = AttendanceSpecifications.buildSpecification(search, date);
            results = attendanceRepository.findAll(spec);
        } else {
            results = attendanceRepository.findByStartDateAndStatus(today, status);
        }

        List<AttendanceListDTO> dtoList = new ArrayList<>();
        for (Attendance attendance : results) {
            dtoList.add(convertToDTO(attendance));
        }

        return dtoList;
    }

    /**
     * Attendance를 AttendanceListDTO로 변환
     */
    public AttendanceListDTO convertToDTO(Attendance attendance) {
        AttendanceListDTO dto = new AttendanceListDTO();
        dto.setStartDate(attendance.getStartDate());
        dto.setTimeIn(attendance.getTimeIn());
        dto.setTimeOut(attendance.getTimeOut());
        dto.setStatus(attendance.getStatus());

        Employees emp = attendance.getEmployees();
        if (emp != null) {
            dto.setEmpName(emp.getName());
            dto.setEmpNo(emp.getEmpNo());
            dto.setDeptName(emp.getDept().getDeptName());
            dto.setJobId(emp.getJobId());
        }

        return dto;
    }



}
