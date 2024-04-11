package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.Annual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MyAnnualRepository extends JpaRepository<Annual, String> {
    Optional<Annual> findByAnnualYearAndEmployees_Id(String annualYear, String empId);
}
