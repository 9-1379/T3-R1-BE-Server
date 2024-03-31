package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EmpBadgeRepository extends JpaRepository<EmpBadge, Long> {

    boolean existsByEmployeesAndBadgeMaster(Employees employee, BadgeMaster decadeBadge);

    boolean existsByEmployeesAndBadgeMasterAndDate(Employees employee, BadgeMaster leaveBadge, Date todayDate);
}
