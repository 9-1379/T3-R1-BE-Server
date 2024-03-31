package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT emp_id " +
            "FROM (SELECT a.emp_id, " +
            "             COUNT(a.id) AS overtime_count " +
            "      FROM attendance a " +
            "      WHERE (TIME(a.time_out) > '19:00:00' OR TIME(a.time_in) < '06:00:00') " +
            "      AND a.start_date BETWEEN :startOfWeek AND :endOfWeek " +
            "      GROUP BY a.emp_id) AS sub " +
            "WHERE overtime_count >= 3",
            nativeQuery = true)
    List<String> findEmployeesWithOvertimeLastWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);

}
