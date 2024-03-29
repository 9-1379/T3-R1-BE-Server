package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.dto.BadgeProjection;
import com.nineties.bhr.badge.service.AdminBadgeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/badge")
public class AdminBadgeController {

    private final AdminBadgeService adminBadgeService;

    public AdminBadgeController(AdminBadgeService adminBadgeService) {
        this.adminBadgeService = adminBadgeService;
    }

    @GetMapping("/list")
    public List<BadgeProjection> showBadgeList() {
        return adminBadgeService.showBadgeList();
    }
}
