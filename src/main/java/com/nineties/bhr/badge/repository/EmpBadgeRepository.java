package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmpBadgeRepository extends JpaRepository<EmpBadge, Long> {

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EmpBadge e WHERE e.employees = :employee AND e.badgeMaster = :badgeMaster AND (e.endDate > :date OR e.endDate IS NULL)")
    boolean badgeCheck (@Param("employee") Employees employee, @Param("badgeMaster") BadgeMaster badgeMaster, @Param("date") Date date);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EmpBadge e WHERE e.employees = :employee AND e.badgeMaster = :badgeMaster AND e.date > :startDate AND e.date < :endDate AND e.endDate > :endDate")
    boolean annualBadgeCheck(@Param("employee") Employees employee, @Param("badgeMaster") BadgeMaster badgeMaster, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    List<EmpBadge> findByBadgeMaster(BadgeMaster badgeMaster);
}
