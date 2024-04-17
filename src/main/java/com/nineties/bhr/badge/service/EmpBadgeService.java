package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpBadgeService {

    public final EmpBadgeRepository empBadgeRepository;

    public EmpBadgeService(EmpBadgeRepository empBadgeRepository) {
        this.empBadgeRepository = empBadgeRepository;
    }

    public List<EmpBadge> getRecentBadgesForCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String empId = ((UserDetails) principal).getUsername();
            return empBadgeRepository.findTop3ByEmployees_IdOrderByDateDesc(empId);
        } else {
            return List.of(); // 현재 사용자 정보가 없으면 빈 리스트 반환
        }
    }
}
