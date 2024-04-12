package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.domain.AnnualPK;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface AnnualRepository extends JpaRepository<Annual, AnnualPK> {

    Optional<Annual> findByAnnualYearAndEmployees(String annualYear, Employees employees);

    Annual findByEmployeesAndAnnualYear(Employees employees, String annualYear);

    List<Annual> findByAnnualYear(String annualYear);
}


    

