package com.nineties.bhr.emp.repository;

import com.nineties.bhr.dept.domain.Dept;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.hrcard.dto.EmpListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String> {


    Optional<Employees> findById(String id);
    Employees findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query("SELECT MAX(e.empNo) FROM Employees e")
    Long findMaxEmpNo();

    @Query("SELECT e.empNo, e.name, e.dept.deptName, e.email from Employees e")
    List<EmpListDTO> findAllByDept(Dept dept);
}
