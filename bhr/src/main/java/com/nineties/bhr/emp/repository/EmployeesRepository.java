package com.nineties.bhr.emp.repository;

import com.nineties.bhr.emp.domain.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, String> {


    Optional<Employees> findById(String id);
    Employees findByUsername(String username);
    Boolean existsByUsername(String username);

    Long countBy();
}
