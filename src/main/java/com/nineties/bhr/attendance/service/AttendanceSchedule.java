package com.nineties.bhr.attendance.service;

import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.attendance.domain.AttendanceStatus;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AttendanceSchedule {

    private final EmployeesRepository employeeRepository;
    private final AnnualListRepository annualLeaveRepository;
    private final AttendanceRepository attendanceRepository;

    public AttendanceSchedule(EmployeesRepository employeeRepository, AnnualListRepository annualLeaveRepository, AttendanceRepository attendanceRepository) {
        this.employeeRepository = employeeRepository;
        this.annualLeaveRepository = annualLeaveRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // 매일 밤 12시에 실행 (주말 제외)
    // 모든 직원 (퇴사, 휴직 제외) 의 그날 attendance data 넣기
    // 휴가인 직원만 휴가로 놓고 다른 직원들은 default로 결근으로 넣음
    @Scheduled(cron = "0 0 0 * * MON-FRI")
    public void generateDailyAttendanceRecords() {
        Date today = new Date();
        List<Employees> allEmployees = employeeRepository.findActiveEmployees();
        List<Employees> onLeaveEmployees = annualLeaveRepository.findByDateWithinAnnualLeave(today);

        for (Employees employee : allEmployees) {
            Attendance attendance = new Attendance();
            attendance.setStartDate(today);
            attendance.setEmployees(employee);

            if (onLeaveEmployees.contains(employee)) {
                attendance.setStatus(AttendanceStatus.ON_LEAVE);
            } else {
                attendance.setStatus(AttendanceStatus.ABSENT);
            }

            attendanceRepository.save(attendance);
        }
    }
}
