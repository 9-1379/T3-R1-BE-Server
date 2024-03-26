package com.nineties.bhr.login.controller;


import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.join.dto.JoinDTO;
import com.nineties.bhr.join.service.JoinService;
import com.nineties.bhr.login.dto.RoleInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping("/api")
public class LoginController {

    @GetMapping(value = "/login")
    public ResponseEntity<RoleInfo> login_form() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        RoleInfo roleInfo = new RoleInfo(role);
        return ResponseEntity.ok(roleInfo); // RoleInfo 객체를 JSON으로 변환하여 반환
    }




}
