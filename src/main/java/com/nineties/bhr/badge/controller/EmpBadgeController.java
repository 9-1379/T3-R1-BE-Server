package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{empId}")
    public ResponseEntity<List<EmpBadge>> getRecentBadgesForEmployee(@PathVariable String empId) {
        Pageable pageable = PageRequest.of(0,3, Sort.by("date").descending());
            List<EmpBadge> recentBadges = badgeService.getRecentBadgesForEmployee(empId, pageable);
         return ResponseEntity.ok(recentBadges);

        }
    }

