package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.CustomUserDetails;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    private final EmployeesRepository employeesRepository;

    public CustomEmployeeDetailsService (EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employees userData = employeesRepository.findByUsername(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }


}

