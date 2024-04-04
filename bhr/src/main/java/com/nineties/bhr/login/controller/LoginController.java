package com.nineties.bhr.login.controller;


import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import com.nineties.bhr.login.dto.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private EmployeesRepository employeesRepository;

    @GetMapping(value = "/login")
    public ResponseEntity<RoleInfo> login_form() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName(); // 로그인한 사용자의 username

        // username을 사용하여 Employees 엔티티를 찾습니다.
        Employees employee = employeesRepository.findByUsername(username);
        // Employee가 null이면 예외를 던집니다.
        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        String EmpId = employee.getId(); // Employee 엔티티에서 empId를 가져옵니다.
        RoleInfo roleInfo = new RoleInfo(role);
        return ResponseEntity.ok(roleInfo); // RoleInfo 객체를 JSON으로 변환하여 반환
    }




}
