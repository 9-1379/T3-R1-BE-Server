package com.nineties.bhr.admin.service;

import com.nineties.bhr.admin.mapper.EmployeeMapper;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AdminDashBoardService adminDashBoardService;

    private final EmployeeMapper employeeMapper;


    public AttendanceService(AttendanceRepository attendanceRepository, EmployeesRepository employeesRepository, AdminDashBoardService adminDashBoardService, EmployeeMapper employeeMapper) {
        this.attendanceRepository = attendanceRepository;
        this.adminDashBoardService = adminDashBoardService;
        this.employeeMapper = employeeMapper;
    }

    public Long countEarlyEmployees() {
        return attendanceRepository.countAllByTimeInIsNotNull();
    }

//    public List<AttendanceCountDto> getEarlyEmployees() {
//        return attendanceRepository.countAllByTimeInIsNotNull()
//                .map(employeeMapper::attendanceCountDto)
//                .collect(Collectors.toList());
//    }
}

