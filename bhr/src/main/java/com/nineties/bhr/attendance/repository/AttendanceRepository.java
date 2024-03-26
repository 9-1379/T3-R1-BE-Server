package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.timeIn < :nineAm")
    Long countByTimeInBeforeNineAm(@Param("nineAm") LocalDateTime nineAm);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.timeIn IS NULL AND a.startDate = :today")
    Long countByNotYetAttended(@Param("today") LocalDate today);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.timeIn IS NOT NULL")
    Long countAllByTimeInIsNotNull();
}
