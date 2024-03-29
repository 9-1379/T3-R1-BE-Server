package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.EmpBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpBadgeRepository extends JpaRepository<EmpBadge, Long> {
}
