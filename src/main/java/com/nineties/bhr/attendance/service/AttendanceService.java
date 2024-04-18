package com.nineties.bhr.attendance.service;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.domain.AttendanceStatus;
import com.nineties.bhr.attendance.dto.AttendanceDTO;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    /**
     * 출근
     *
     * 출근은 오전 6시 ~ 오후 23시 59분 까지 가능
     * 오늘 날짜에 이미 time in이 존재할 경우 이미 출근 기록 있음
     * 오늘 날짜의 attendance 레코드가 존재하지 않을 경우 새로 생성
     */
    public AttendanceDTO recordStartWork(String employeeId) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // 현재 시간 확인
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalTime currentTime = now.toLocalTime();

        // 출근 시간 범위 확인
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("출근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있습니다.");
        }

        // 오늘 날짜
        LocalDate todayDate = LocalDate.now();
        Date today = Date.from(todayDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 이미 출근한 기록이 있는지 확인
        Optional<Attendance> existingRecord = attendanceRepository
                .findFirstByEmployeesAndStartDateAndTimeInIsNotNull(employee, today);

        if (existingRecord.isPresent()) {
            throw new RuntimeException("Start work record already exists for today.");
        }

        // 오늘 날짜의 attendance 찾기
        Attendance attendance = attendanceRepository.findByEmployeesAndStartDate(employee, today);

        // 만약 없다면 새로 생성
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setStartDate(today);
            attendance.setEmployees(employee);
        }

        // 출근 상태 설정
        if (currentTime.isBefore(LocalTime.of(9, 0, 1))) {
            attendance.setStatus(AttendanceStatus.PRESENT);
        } else {
            attendance.setStatus(AttendanceStatus.LATE);
        }

        attendance.setTimeIn(new Date());

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return convertToDto(savedAttendance);
    }

    /**
     * 퇴근
     *
     * 퇴근은 언제나 가능
     * 오전 6시 이전일 경우 전날에 대한 퇴근으로 처리
     */
    public AttendanceDTO recordEndWork(String employeeId) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));
        // 현재 시간
        Calendar now = Calendar.getInstance();

        // 현재 시간이 6시 이전인지 확인
        Calendar sixAmToday = (Calendar) now.clone();
        sixAmToday.set(Calendar.HOUR_OF_DAY, 6);
        sixAmToday.set(Calendar.MINUTE, 0);
        sixAmToday.set(Calendar.SECOND, 0);
        sixAmToday.set(Calendar.MILLISECOND, 0);

        Date targetDate = now.getTime();
        if (now.before(sixAmToday)) {
            // 만약 6시 이전이라면 전날 데이터 가져옴
            now.add(Calendar.DATE, -1);
            targetDate = now.getTime();
        }

        Attendance attendance = attendanceRepository.findByEmployeesAndStartDate(employee, targetDate);

        if (attendance == null) {
            throw new IllegalStateException("해당 직원과 해당 날짜에 맞는 데이터를 찾을 수 없습니다.");
        }

        if (attendance.getTimeOut() != null) {
            throw new IllegalStateException("퇴근 기록이 이미 존재합니다.");
        }

        if (attendance.getTimeIn() == null) {
            throw new IllegalStateException("출근 기록이 존재하지 않습니다.");
        }

        attendance.setEndDate(targetDate);
        attendance.setTimeOut(new Date());
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToDto(updatedAttendance);
    }

    // 출근 기록
    public AttendanceDTO getAttendanceRecord(String employeeId) {
        LocalDate today = LocalDate.now();
        Optional<Attendance> attendanceRecord = attendanceRepository.findFirstByEmployeesIdAndStartDate(employeeId, today);
        return attendanceRecord.map(this::convertToDto).orElse(null);
    }

    /**
     * 출근 현황
     */
    public Map<String, Integer> getMonthlyAttendanceSummary(String employeeId) {
        int currentMonth = LocalDate.now().getMonthValue();
        List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndMonth(employeeId, currentMonth);
        Map<String, Integer> summary = new HashMap<>();
        summary.put("출근", 0);
        summary.put("지각", 0);
        summary.put("결근", 0);

        for (Attendance attendance : attendances) {
            switch (attendance.getStatus()) {
                case PRESENT:
                    summary.put("출근", summary.get("출근") + 1);
                    break;
                case LATE:
                    summary.put("지각", summary.get("지각") + 1);
                    break;
                case ABSENT:
                    summary.put("결근", summary.get("결근") + 1);
                    break;
            }
        }
        return summary;
    }

    private AttendanceDTO convertToDto(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setStartDate(attendance.getStartDate());
        dto.setEndDate(attendance.getEndDate());
        dto.setTimeIn(attendance.getTimeIn());
        dto.setTimeOut(attendance.getTimeOut());
        if (attendance.getEmployees() != null) {
            dto.setEmployeeId(attendance.getEmployees().getId());
        }
        return dto;
    }
}
