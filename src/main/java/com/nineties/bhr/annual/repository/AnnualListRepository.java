package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.AnnualList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AnnualListRepository extends JpaRepository<AnnualList, Long> {
    List<AnnualList> findByEmployees_Username(String username);

    @Query("SELECT COUNT(a) FROM AnnualList a WHERE :today BETWEEN a.startDate AND a.endDate")
    int countByDateWithinAnnualLeave(@Param("today") Date today);
}
