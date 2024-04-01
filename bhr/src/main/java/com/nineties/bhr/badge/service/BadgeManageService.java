package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.BadgeStatus;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class BadgeManageService {

    private final BadgeMasterRepository badgeMasterRepository;
    private final EmpBadgeRepository empBadgeRepository;

    public BadgeManageService(BadgeMasterRepository badgeMasterRepository, EmpBadgeRepository empBadgeRepository) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.empBadgeRepository = empBadgeRepository;
    }

    @Transactional
    public boolean disableBadgeAndRelatedEmpBadges(String badgeName) {
        BadgeMaster badgeMaster = badgeMasterRepository.findByBadgeName(badgeName);
        if (badgeMaster != null) {
            // 배지 상태를 Disabled로 설정
            badgeMaster.setStatus(BadgeStatus.Disabled);
            badgeMasterRepository.save(badgeMaster);

            // 현재 날짜
            Date today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

            // 해당 배지를 받은 모든 EmpBadge 중 아직 종료되지 않은 배지의 endDate를 오늘로 설정
            List<EmpBadge> relatedEmpBadges = empBadgeRepository.findByBadgeMaster(badgeMaster);
            relatedEmpBadges.stream()
                    .filter(empBadge -> empBadge.getEndDate() == null || empBadge.getEndDate().after(today))
                    .forEach(empBadge -> {
                        empBadge.setEndDate(today); // 오늘 날짜로 endDate 설정
                         empBadgeRepository.save(empBadge);
                    });

            return true; // 성공적으로 비활성화 및 관련 EmpBadge 종료 처리됨
        }
        return false; // 해당 이름의 배지를 찾을 수 없음
    }


}
