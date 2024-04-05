package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AnnualListRepository extends JpaRepository<AnnualList, Long> {
    List<AnnualList> findByEmployees_Username(String username);

    List<AnnualList> findByStartDate(Date today);

    List<AnnualList> findByStartDateBeforeAndEndDateAfterOrEndDateIsNull(Date startDate, Date endDate);

    List<AnnualList> findByAnnualYearAndEmployees(String currentYear, Employees employee);
}
