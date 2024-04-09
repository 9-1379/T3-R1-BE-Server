package com.nineties.bhr.emp.dto;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Employees employees;

    public CustomUserDetails(Employees employees) {

        this.employees = employees;
    }

    private GrantedAuthority getAuthority(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        switch (employees.getAuthorization()) {
            case HRMANAGER : authorityList.add(getAuthority(Role.HRMANAGER));
            case MANAGER : authorityList.add(getAuthority(Role.MANAGER));
            case EMPLOYEE : authorityList.add(getAuthority(Role.EMPLOYEE));
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return employees.getPassword();
    }

    @Override
    public String getUsername() {
        return employees.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
