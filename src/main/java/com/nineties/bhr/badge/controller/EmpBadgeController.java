package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.service.BadgeService;
import com.nineties.bhr.badge.service.EmpBadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpBadgeController {

    private final EmpBadgeService empBadgeService;

    @Autowired
    public EmpBadgeController(EmpBadgeService empBadgeService) {
        this.empBadgeService = empBadgeService;
    }

    @GetMapping("/badge")
    public List<EmpBadge> getRecentBadgesForCurrentUser() {
        return empBadgeService.getRecentBadgesForCurrentUser();
    }
}

