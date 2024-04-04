package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmpBadgeRepository extends JpaRepository<EmpBadge, Long> {

    boolean existsByEmployeesAndBadgeMasterAndEndDateAfterOrEndDateIsNull(Employees employee, BadgeMaster decadeBadge, Date date);

    boolean existsByEmployeesAndBadgeMasterAndDateAfterAndDateBeforeAndEndDateAfter(Employees employee, BadgeMaster leaveBadge, Date annualListStartDate, Date todayDate, Date todayDate1);

    boolean existsByEmployeesAndBadgeMasterAndEndDateAfterOrIsNull(Employees employee, BadgeMaster workLifeBadge, Date todayDate);

    List<EmpBadge> findByBadgeMaster(BadgeMaster badgeMaster);
}
