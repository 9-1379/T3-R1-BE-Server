package com.nineties.bhr.emp.repository;


import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String> {


    Optional<Employees> findById(String id);
    Employees findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query("SELECT MAX(e.empNo) FROM Employees e")
    Long findMaxEmpNo();

    @Query("SELECT e.empNo as empNo, e.name as name, e.email as email, e.position as position, e.jobId as jobId, e.profilePicture as profilePicture, e.introduction as introduction, e.dept.deptName as deptName FROM Employees e")
    List<EmployeeProjection> findEmpNoNameDeptNameEmail();

    List<Employees> findAll();

    @Query("SELECT e FROM Employees e WHERE e.hireDate <= :cutoffDate")
    List<Employees> findByHireDateBefore(Date cutoffDate);

}
