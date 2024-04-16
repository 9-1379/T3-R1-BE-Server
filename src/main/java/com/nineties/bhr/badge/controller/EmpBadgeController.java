package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/myBadge")
public class EmpBadgeController {

    private final BadgeService badgeService;

    @Autowired
    public EmpBadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<EmpBadge>> getRecentBadgesForCurrentUser() {
        List<EmpBadge> recentBadges = badgeService.getRecentBadgesForCurrentUser();
        return ResponseEntity.ok(recentBadges);
    }
}

