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

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

    // 이번 달 출근 현황
    public Map<String, Integer> getMonthlyAttendanceSummary(String employeeId) {
        Map<String, Integer> summary = new HashMap<>();
        int attendanceCount = 0;
        int lateCount = 0;
        int absenceCount = 0;

        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfPeriod = today.minusDays(1);

        // 주말에 해당하는 날짜를 식별합니다.
        Set<LocalDate> weekendDays = new HashSet<>();
        for (LocalDate date = startOfMonth; date.isBefore(endOfPeriod.plusDays(1)); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendDays.add(date);
            }
        }

        List<Attendance> monthlyRecords = attendanceRepository
                .findByEmployeesAndStartDateBetweenOrderByStartDateAsc(employee, getStartOfMonth(startOfMonth), getEndOfPeriod(endOfPeriod));

        Set<LocalDate> recordedDays = new HashSet<>();
        for (Attendance record : monthlyRecords) {
            LocalDate recordDate = Instant.ofEpochMilli(record.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            // 주말에 해당하는 경우 개수에 추가하지 않습니다.
            if (weekendDays.contains(recordDate)) {
                // 주말에 출근한 경우 출근 숫자에 포함시킵니다.
                attendanceCount++;
                continue;
            }

            recordedDays.add(recordDate);

            if (record.getTimeIn() != null) {
                attendanceCount++;

                // 지각 여부를 체크합니다.
                if (record.getTimeIn().after(java.sql.Time.valueOf("09:00:00"))) {
                    lateCount++;
                }
            }
        }

        // 주말에 결근하거나 지각한 경우 개수에서 빼줍니다.
        for (LocalDate weekendDay : weekendDays) {
            if (recordedDays.contains(weekendDay)) {
                recordedDays.remove(weekendDay);
            }
        }

        // 주말을 제외한 실제 근무일 수를 계산합니다.
        int workingDays = (int) (ChronoUnit.DAYS.between(startOfMonth, endOfPeriod) + 1) - weekendDays.size();

        // 총 결근 횟수를 계산합니다.
        absenceCount = workingDays - recordedDays.size();

        summary.put("출근", attendanceCount);
        summary.put("지각", lateCount);
        summary.put("결근", absenceCount);

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

    private Date getStartOfMonth(LocalDate date) {
        LocalDateTime startOfMonth = date.atStartOfDay();
        return Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndOfPeriod(LocalDate date) {
        LocalDateTime endOfPeriod = date.atTime(23, 59, 59, 999999999);
        return Date.from(endOfPeriod.atZone(ZoneId.systemDefault()).toInstant());
    }
}
