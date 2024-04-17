package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.BadgeStatus;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.dto.BadgeProjection;
import com.nineties.bhr.badge.exception.BadgeNotFoundException;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BadgeManageService {

    private final BadgeMasterRepository badgeMasterRepository;
    private final EmpBadgeRepository empBadgeRepository;
    private final ActiveBadgeService activeBadgeService;

    public BadgeManageService(BadgeMasterRepository badgeMasterRepository, EmpBadgeRepository empBadgeRepository, ActiveBadgeService activeBadgeService) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.empBadgeRepository = empBadgeRepository;
        this.activeBadgeService = activeBadgeService;
    }

    //배지 리스트 출력
    public List<BadgeProjection> showBadgeList() {
        return badgeMasterRepository.findAllProjectedBy();
    }

    //배지 비활성화
    @Transactional
    public boolean disableBadgeAndRelatedEmpBadges(String badgeName) {
        BadgeMaster badgeMaster = badgeMasterRepository.findByBadgeName(badgeName);
        if (badgeMaster == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Disabled로 설정
        badgeMaster.setStatus(BadgeStatus.Disabled);
        badgeMasterRepository.save(badgeMaster);

        // 전날의 날짜
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Date yesterdayDate = Date.from(yesterday.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // 해당 배지를 받은 모든 EmpBadge 중 아직 종료되지 않은 배지의 endDate를 전날로 설정
        List<EmpBadge> relatedEmpBadges = empBadgeRepository.findByBadgeMaster(badgeMaster);
        relatedEmpBadges.stream()
                .filter(empBadge -> empBadge.getEndDate() == null || empBadge.getEndDate().after(yesterdayDate))
                .forEach(empBadge -> {
                    empBadge.setEndDate(yesterdayDate); // 전날 날짜로 endDate 설정
                    empBadgeRepository.save(empBadge);
                });

        log.info("{} 배지 비활성화 완료", badgeMaster.getBadgeName());
        return true; // 성공적으로 비활성화 및 관련 EmpBadge 종료 처리됨
    }

    // 배지 활성화
    public void activateBadgeByName(String badgeName) throws BadgeNotFoundException {
        switch (badgeName) {
            case "Z세대":
                activeBadgeService.activateZGenerationBadge();
                break;
            case "Y세대":
                activeBadgeService.activateYGenerationBadge();
                break;
            case "X세대":
                activeBadgeService.activateXGenerationBadge();
                break;
            case "귀여운건 나도 알아":
                activeBadgeService.activateAndAssignNewbieBadge();
                break;
            case "회사 지박령":
                activeBadgeService.activateCompanyNightOwlBadge();
                break;
            case "전설속의 그대":
                activeBadgeService.activateLegendBadge();
                break;
            case "도비는 자유에요":
                activeBadgeService.activateDobbyIsFreeBadge();
                break;
            case "워라벨 마스터":
                activeBadgeService.activateWorkLifeBalanceBadge();
                break;
            default:
                throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }
    }


}
