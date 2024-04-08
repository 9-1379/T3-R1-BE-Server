package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.AnnualList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnualListRepository extends JpaRepository<AnnualList, Long> {
    List<AnnualList> findByEmployees_Username(String username);
}
