package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query(value = "SELECT emp_id " +
            "FROM (SELECT a.emp_id, " +
            "             COUNT(*) AS overtime_count " +
            "      FROM attendance a " +
            "      WHERE ( " +
            "               (a.start_date = a.end_date AND TIME(a.time_out) > '19:00:00') " +
            "               OR " +
            "               (a.start_date < a.end_date AND TIME(a.time_out) < '06:00:00') " +
            "            ) " +
            "        AND a.start_date BETWEEN :startOfWeek AND :endOfWeek " +
            "      GROUP BY a.emp_id) AS subquery " +
            "WHERE overtime_count >= 3", nativeQuery = true)
    List<String> findEmployeesWithOvertimeLastWeek(@Param("startOfWeek") Date startOfWeek, @Param("endOfWeek") Date endOfWeek);


}
