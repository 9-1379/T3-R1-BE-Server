package com.nineties.bhr.emp.repository;

import com.nineties.bhr.emp.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {

    Dept findByDeptName(String deptName);

    @Query("SELECT d.deptName FROM Dept d")
    List<String> findAllDeptNames();
}

