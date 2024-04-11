package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {
    Optional<Attendance> findTopByEmployeesAndStartDateAfterOrderByStartDateDesc(Employees employee, Date start);
    Optional<Attendance> findTopByEmployeesAndStartDateBetweenOrderByStartDateDesc(Employees employee, Date start, Date end);


    Optional<Attendance> findTopByEmployeesAndTimeInBeforeOrderByTimeInDesc(Employees employee, Date current);

    Optional<Attendance> findFirstByEmployeesIdOrderByStartDateAsc(String employeeId);

    Optional<Attendance> findFirstByEmployeesIdOrderByStartDateDesc(String employeeId);

    Optional<Attendance> findFirstByEmployeesAndStartDateBetweenOrderByStartDateAsc(Employees employee, Date startOfDay, Date endOfDay);

    List<Attendance> findByEmployeesAndStartDateBetweenOrderByStartDate(Employees employee, LocalDate startOfMonth, LocalDate endOfMonth);

    List<Attendance> findByEmployeesAndStartDateBetweenOrderByStartDateAsc(Employees employee, Date startOfMonth, Date endOfPeriod);

    Optional<Attendance> findFirstByEmployeesIdAndStartDate(String employeeId, LocalDate today);
}
