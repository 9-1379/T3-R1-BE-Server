package com.nineties.bhr.attendance.service;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.dto.AttendanceDTO;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public AttendanceDTO recordStartWork(String employeeId) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalTime currentTime = now.toLocalTime();
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("출근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있습니다.");
        }

        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        if (currentTime.isBefore(LocalTime.of(6, 0))) {
            startOfDay = startOfDay.minusDays(1);
        }

        Date todayStart = getStartOfDay(startOfDay);
        Date endOfDay = getEndOfDay(startOfDay);

        Optional<Attendance> existingRecord = attendanceRepository
                .findTopByEmployeesAndStartDateBetweenOrderByStartDateDesc(employee, todayStart, endOfDay);

        if (existingRecord.isPresent()) {
            throw new RuntimeException("Start work record already exists for today.");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployees(employee);
        attendance.setStartDate(new Date());
        attendance.setTimeIn(new Date());
        Attendance savedAttendance = attendanceRepository.save(attendance);

        return convertToDto(savedAttendance);
    }

    public AttendanceDTO recordEndWork(String employeeId) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalTime currentTime = now.toLocalTime();
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("퇴근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있습니다.");
        }

        Date current = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        Optional<Attendance> todayStartRecord = attendanceRepository
                .findFirstByEmployeesAndStartDateBetweenOrderByStartDateAsc(employee, getStartOfDay(now), getEndOfDay(now));

        if (!todayStartRecord.isPresent()) {
            throw new RuntimeException("No start work record found for today.");
        }

        Attendance attendance = todayStartRecord.get();

        if (attendance.getTimeIn().compareTo(current) > 0) {
            throw new RuntimeException("End work time cannot be earlier than start work time.");
        }

        if (attendance.getEndDate() != null) {
            throw new RuntimeException("End work already recorded for this entry.");
        }

        attendance.setEndDate(new Date());
        attendance.setTimeOut(new Date());
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToDto(updatedAttendance);
    }

    public AttendanceDTO getAttendanceRecord(String employeeId) {
        Optional<Attendance> attendanceRecord = attendanceRepository
                .findFirstByEmployeesIdOrderByStartDateDesc(employeeId);

        return attendanceRecord.map(this::convertToDto).orElse(null);
    }

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

        List<Attendance> monthlyRecords = attendanceRepository
                .findByEmployeesAndStartDateBetweenOrderByStartDateAsc(employee, getStartOfMonth(startOfMonth), getEndOfPeriod(endOfPeriod));

        Set<LocalDate> recordedDays = new HashSet<>();
        for (Attendance record : monthlyRecords) {
            LocalDate recordDate = Instant.ofEpochMilli(record.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            recordedDays.add(recordDate);

            if (record.getTimeIn() != null) {
                attendanceCount++;
                if (record.getTimeIn().after(java.sql.Time.valueOf("09:00:00"))) {
                    lateCount++;
                }
            }
        }

        long daysInPeriod = ChronoUnit.DAYS.between(startOfMonth, endOfPeriod) + 1;
        absenceCount = (int) (daysInPeriod - recordedDays.size());

        summary.put("출석", attendanceCount);
        summary.put("지각", lateCount);
        summary.put("결석", absenceCount);

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

    private Date getStartOfDay(LocalDateTime dateTime) {
        LocalDateTime startOfDay = dateTime.toLocalDate().atStartOfDay();
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndOfDay(LocalDateTime dateTime) {
        LocalDateTime endOfDay = dateTime.toLocalDate().atTime(23, 59, 59, 999999999);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
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
