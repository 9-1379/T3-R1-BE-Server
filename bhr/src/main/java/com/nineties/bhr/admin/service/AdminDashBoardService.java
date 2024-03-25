package com.nineties.bhr.admin.service;

import com.nineties.bhr.admin.dto.AdminDTO;
import com.nineties.bhr.admin.mapper.EmployeeMapper;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminDashBoardService {
    private final EmployeesRepository employeesRepository;
    private final EmployeeMapper employeeMapper;


    @Autowired
    public AdminDashBoardService(EmployeesRepository employeesRepository, EmployeeMapper employeeMapper) {
        this.employeesRepository = employeesRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<AdminDTO> getAllEmployees() {
        return employeesRepository.findAll().stream()
                .map(employeeMapper::adminDTO)
                .collect(Collectors.toList());
    }

//    public List<AttendanceDTO> findEmployeesArrivedBefore9AM() {
//        // 9시 이전 출근자 조회
//        Timestamp nineAMTimestamp = Timestamp.valueOf("2024-03-25 09:00:00"); // 출근 기준 시간 설정
//        List<Attendance> attendances = employeesRepository.findByTimeInBefore(nineAMTimestamp);
//        return attendances.stream()
//                .map(employeeMapper::attendanceDTO)
//                .collect(Collectors.toList());
//    }

    public Long getCount() {
        return employeesRepository.countBy();
    }

    public Long countEmployeesBeforeNinAM() {
        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date nineAM = cal.getTime();

        return employeesRepository.countByTimeInBefore(nineAM);
    }

}

