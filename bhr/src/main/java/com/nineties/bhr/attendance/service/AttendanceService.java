package com.nineties.bhr.attendance.service;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public Attendance recordStartWork(Long employeeId) {
        Employees employee = employeesRepository.findById(String.valueOf(employeeId))
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        Attendance attendance = new Attendance();
        attendance.setStartDate(new Date()); // 시스템의 현재 날짜
        attendance.setTimeIn(new Date()); // 시스템의 현재 시간
        attendance.setEmployees(employee); // 로그인한 직원의 ID
        return attendanceRepository.save(attendance);
    }

    public Attendance recordEndWork(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found with id: " + attendanceId));

        attendance.setEndDate(new Date()); // 시스템의 현재 날짜
        attendance.setTimeOut(new Date()); // 시스템의 현재 시간
        return attendanceRepository.save(attendance);
    }
}
