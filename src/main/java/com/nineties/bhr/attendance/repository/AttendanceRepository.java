package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


    List<Attendance> findByEmployeesAndStartDateBetweenOrderByStartDate(Employees employee, LocalDate startOfMonth, LocalDate endOfMonth);

    List<Attendance> findByEmployeesAndStartDateBetweenOrderByStartDateAsc(Employees employee, Date startOfMonth, Date endOfPeriod);

    Optional<Attendance> findFirstByEmployeesIdAndStartDate(String employeeId, LocalDate today);
}
