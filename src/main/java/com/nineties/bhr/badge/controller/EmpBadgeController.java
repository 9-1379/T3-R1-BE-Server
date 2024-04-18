package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.dto.MyBadgeDTO;
import com.nineties.bhr.badge.service.EmpBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpBadgeController {

    private final EmpBadgeService empBadgeService;

    @Autowired
    public EmpBadgeController(EmpBadgeService empBadgeService) {
        this.empBadgeService = empBadgeService;
    }

    @GetMapping("/emp/badge")
    public List<MyBadgeDTO> getRecentBadgesForCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return empBadgeService.getRecentBadgesForCurrentUser(username);
    }

    @GetMapping("/hrCard/badges/{id}")
    public List<MyBadgeDTO> getBadgeList(@PathVariable String id) {
        return empBadgeService.getBadgesForHrCard(id);
    }
}

