package com.nineties.bhr.emp.repository;


import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileProjection;
import com.nineties.bhr.emp.dto.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String> {


    Optional<Employees> findById(String id);

    Employees findByUsername(String username);

    @Query("SELECT e.id as id, e.name as name, e.dept.deptName as deptName, e.position as position, e.introduction as introduction, e.profilePicture as profilePicture FROM Employees e where e.username = :username")
    EmployeeProfileProjection findEmpProfile(@Param("username") String username);
    Boolean existsByUsername(String username);

    @Query("SELECT MAX(e.empNo) FROM Employees e")
    Long findMaxEmpNo();

    @Query("SELECT e.id as id, e.empNo as empNo, e.name as name, e.email as email, e.position as position, e.jobId as jobId, e.profilePicture as profilePicture, e.introduction as introduction, e.dept.deptName as deptName FROM Employees e ORDER BY e.empNo")
    List<EmployeeProjection> findEmpNoNameDeptNameEmail();

    List<Employees> findAll();

    long count();

    @Query("SELECT e FROM Employees e WHERE e.status <> 'REST' AND e.status <> 'LEAVE'")
    List<Employees> findActiveEmployees();

    @Query("SELECT count(e) FROM Employees e WHERE e.status <> 'REST' AND e.status <> 'LEAVE'")
    long countActiveEmployees();

    @Query("SELECT e FROM Employees e WHERE e.hireDate <= :cutoffDate")
    List<Employees> findByHireDateBefore(Date cutoffDate);

}
