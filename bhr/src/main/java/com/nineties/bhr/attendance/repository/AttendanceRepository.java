package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findTopByEmployeesAndStartDateAfterOrderByStartDateDesc(Employees employee, Date start);
    Optional<Attendance> findTopByEmployeesAndStartDateBetweenOrderByStartDateDesc(Employees employee, Date start, Date end);
}
