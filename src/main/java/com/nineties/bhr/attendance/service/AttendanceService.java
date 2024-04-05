package com.nineties.bhr.attendance.service;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.dto.AttendanceDTO;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

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
        // 출근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있도록 합니다.
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("출근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있습니다.");
        }

        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        if (currentTime.isBefore(LocalTime.of(6, 0))) {
            startOfDay = startOfDay.minusDays(1);
        }

        Date todayStart = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(startOfDay.plusDays(1).minusSeconds(1).atZone(ZoneId.systemDefault()).toInstant());

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
        // 퇴근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있도록 합니다.
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(23, 59))) {
            throw new RuntimeException("퇴근 버튼은 오전 6시부터 오후 11시 59분까지만 누를 수 있습니다.");
        }

        Date current = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        // 현재 날짜와 일치하는 출근 기록을 찾습니다.
        Optional<Attendance> todayStartRecord = attendanceRepository
                .findFirstByEmployeesAndStartDateBetweenOrderByStartDateAsc(employee, getStartOfDay(now), getEndOfDay(now));

        if (!todayStartRecord.isPresent()) {
            throw new RuntimeException("No start work record found for today.");
        }

        Attendance attendance = todayStartRecord.get();

        // 퇴근 기록이 출근 기록보다 빠른 경우 에러 처리
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
        AttendanceDTO dto = new AttendanceDTO();

        // 직원의 출근 기록을 가져옵니다.
        Optional<Attendance> startRecord = attendanceRepository.findFirstByEmployeesIdOrderByStartDateAsc(employeeId);
        if (startRecord.isPresent()) {
            dto.setTimeIn(startRecord.get().getTimeIn());
            dto.setStartDate(startRecord.get().getStartDate());
        }

        // 직원의 퇴근 기록을 가져옵니다.
        Optional<Attendance> endRecord = attendanceRepository.findFirstByEmployeesIdOrderByStartDateDesc(employeeId);
        if (endRecord.isPresent()) {
            dto.setTimeOut(endRecord.get().getTimeOut());
            dto.setEndDate(endRecord.get().getEndDate());
        }

        return dto;
    }

    private AttendanceDTO convertToDto(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setStartDate(attendance.getStartDate());
        dto.setEndDate(attendance.getEndDate());
        dto.setTimeIn(attendance.getTimeIn());
        dto.setTimeOut(attendance.getTimeOut());
        dto.setEmployeeId(attendance.getEmployees().getId());
        return dto;
    }

    private Date getStartOfDay(LocalDateTime dateTime) {
        LocalDateTime startOfDay = dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date getEndOfDay(LocalDateTime dateTime) {
        LocalDateTime endOfDay = dateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
