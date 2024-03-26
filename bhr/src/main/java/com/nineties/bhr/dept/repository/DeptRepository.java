package com.nineties.bhr.dept.repository;

import com.nineties.bhr.dept.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {

    Dept findByDeptName(String deptName);
}

