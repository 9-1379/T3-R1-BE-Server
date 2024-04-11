package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.AnnualList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnnualListRepository extends JpaRepository<AnnualList, Long> {
    List<AnnualList> findByEmployees_Username(String username);

    @Query("SELECT COUNT(a) FROM AnnualList a WHERE :today BETWEEN a.startDate AND a.endDate")
    int countByDateWithinAnnualLeave(@Param("today") Date today);

    @Query("SELECT COALESCE(SUM(a.annualCnt), 0) FROM AnnualList a WHERE a.employees.id = :empId AND a.annualYear = :year")
    Long findAnnualCountByEmployeeAndYear(@Param("empId") String empId, @Param("year") String year);
}
