package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.dto.BadgeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeMasterRepository extends JpaRepository<BadgeMaster, Long> {

    List<BadgeProjection> findAllProjectedBy();
}
