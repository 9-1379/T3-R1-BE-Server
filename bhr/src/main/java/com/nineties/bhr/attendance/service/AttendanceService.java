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

    public AttendanceDTO recordStartWork(Long employeeId) {
        Employees employee = employeesRepository.findById(String.valueOf(employeeId))
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        if (now.toLocalTime().isBefore(LocalTime.of(6, 0))) {
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


    public AttendanceDTO recordEndWork(Long employeeId) {
        Employees employee = employeesRepository.findById(String.valueOf(employeeId))
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        if (now.toLocalTime().isBefore(LocalTime.of(6, 0))) {
            startOfDay = startOfDay.minusDays(1);
        }
        Date start = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(startOfDay.plusDays(1).minusSeconds(1).atZone(ZoneId.systemDefault()).toInstant());

        Optional<Attendance> attendanceOptional = attendanceRepository
                .findTopByEmployeesAndStartDateBetweenOrderByStartDateDesc(employee, start, end);

        if (!attendanceOptional.isPresent()) {
            throw new RuntimeException("No start work record found for today.");
        }

        Attendance attendance = attendanceOptional.get();
        if (attendance.getEndDate() != null) {
            throw new RuntimeException("End work already recorded for today.");
        }

        attendance.setEndDate(new Date());
        attendance.setTimeOut(new Date());
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return convertToDto(updatedAttendance);
    }


    private AttendanceDTO convertToDto(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setStartDate(attendance.getStartDate());
        dto.setEndDate(attendance.getEndDate());
        dto.setTimeIn(attendance.getTimeIn());
        dto.setTimeOut(attendance.getTimeOut());
        dto.setEmployeeId(Long.valueOf(attendance.getEmployees().getId()));
        return dto;
    }
}
