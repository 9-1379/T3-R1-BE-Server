package com.nineties.bhr.annual.repository;

import com.nineties.bhr.annual.domain.Annual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualRepository extends JpaRepository<Annual, String> {

}
