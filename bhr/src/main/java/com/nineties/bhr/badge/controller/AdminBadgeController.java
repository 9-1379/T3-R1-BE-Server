package com.nineties.bhr.badge.controller;

import com.nineties.bhr.badge.dto.BadgeProjection;
import com.nineties.bhr.badge.exception.BadgeNotFoundException;
import com.nineties.bhr.badge.service.BadgeManageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/badge")
public class AdminBadgeController {

    private final BadgeManageService badgeManageService;

    public AdminBadgeController(BadgeManageService badgeManageService) {
        this.badgeManageService = badgeManageService;
    }

    @GetMapping("/list")
    public List<BadgeProjection> showBadgeList() {
        return badgeManageService.showBadgeList();
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateBadge(@RequestParam String badgeName) {
        try {
            badgeManageService.activateBadgeByName(badgeName);
            return ResponseEntity.ok(badgeName + " 배지가 활성화되었습니다.");
        } catch (BadgeNotFoundException e) {
            return ResponseEntity.badRequest().body("배지를 찾을 수 없습니다: " + badgeName);
        }
    }

    @PostMapping("/deactivate")
    public ResponseEntity<String> deactivateBadge(@RequestParam String badgeName) {
        boolean success = badgeManageService.disableBadgeAndRelatedEmpBadges(badgeName);
        if (success) {
            return ResponseEntity.ok(badgeName + " 배지가 비활성화되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("배지 비활성화에 실패했습니다. 배지 이름을 확인해주세요.");
        }
    }
}
