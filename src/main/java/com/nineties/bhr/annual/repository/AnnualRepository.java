package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualPK;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualRepository extends JpaRepository<Annual, AnnualPK> {

    Annual findByAnnualYearAndEmployees(String currentYear, Employees employee);
}
