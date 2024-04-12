package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.BadgeStatus;
import com.nineties.bhr.badge.exception.BadgeNotFoundException;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@Slf4j
public class ActiveBadgeService {

    private final BadgeMasterRepository badgeMasterRepository;
    private final EmployeesRepository employeesRepository;
    private final BadgeService badgeService;

    public ActiveBadgeService(BadgeMasterRepository badgeMasterRepository, EmployeesRepository employeesRepository, BadgeService badgeService) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.employeesRepository = employeesRepository;
        this.badgeService = badgeService;
    }

    // 세대별 배지 활성화

    // Z세대 배지 활성화 및 부여
    @Transactional
    public void activateZGenerationBadge() {
        activateAndAssignGenerationBadge("Z세대", 1995, 2009);
    }

    // Y세대 배지 활성화 및 부여
    @Transactional
    public void activateYGenerationBadge() {
        activateAndAssignGenerationBadge("Y세대", 1980, 1994);
    }

    // X세대 배지 활성화 및 부여
    @Transactional
    public void activateXGenerationBadge() {
        activateAndAssignGenerationBadge("X세대", 1964, 1979);
    }

    // 세대별 배지 활성화 및 부여 공통 메서드
    private void activateAndAssignGenerationBadge(String badgeName, int startYear, int endYear) {
        BadgeMaster badge = badgeMasterRepository.findByBadgeName(badgeName);
        if (badge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        badge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(badge);

        LocalDate now = LocalDate.now();
        List<Employees> allEmployees = employeesRepository.findAll();
        for (Employees employee : allEmployees) {
            LocalDate birthday = Instant.ofEpochMilli(employee.getBirthday().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int birthYear = birthday.getYear();
            if (birthYear >= startYear && birthYear <= endYear) {
                badgeService.assignBadge(employee, badge, null); // 종료일 없이 배지 부여
            }
        }

        log.info("{} 배지가 활성화되었으며, 조건에 맞는 모든 직원들에게 배지가 부여되었습니다.", badgeName);
    }

    // 신입사원 배지 활성화
    @Transactional
    public void activateAndAssignNewbieBadge() {
        String badgeName = "귀여운건 나도 알아";
        BadgeMaster newbieBadge = badgeMasterRepository.findByBadgeName(badgeName);

        if (newbieBadge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        newbieBadge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(newbieBadge);

        LocalDate now = LocalDate.now();
        List<Employees> allEmployees = employeesRepository.findAll();
        for (Employees employee : allEmployees) {
            LocalDate hireDate = Instant.ofEpochMilli(employee.getHireDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            // 입사 후 6개월 이내인 직원에게 배지 부여
            if (hireDate.plusMonths(6).isAfter(now)) {
                badgeService.assignBadge(employee, newbieBadge, hireDate.plusMonths(6)); // 배지 종료일은 입사일로부터 6개월 후
            }
        }

        log.info("{} 배지가 활성화되었으며, 조건에 맞는 모든 신입 사원들에게 배지가 부여되었습니다.", badgeName);
    }

    // 회사 지박령 배지 활성화
    @Transactional
    public void activateCompanyNightOwlBadge() {
        String badgeName = "회사 지박령";
        BadgeMaster nightOwlBadge = badgeMasterRepository.findByBadgeName(badgeName);

        if (nightOwlBadge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        nightOwlBadge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(nightOwlBadge);

        badgeService.assignOvertimeBadge();

        log.info("{} 배지가 활성화되었으며, 지난주 야근 기준을 충족하는 직원들에게 배지가 부여되었습니다.", badgeName);
    }

    // 전설속의 그대 배지 활성화
    @Transactional
    public void activateLegendBadge() {
        String badgeName = "전설속의 그대";
        BadgeMaster legendBadge = badgeMasterRepository.findByBadgeName(badgeName);

        if (legendBadge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        legendBadge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(legendBadge);

        badgeService.assignDecadeBadge();

        log.info("{} 배지가 활성화되었으며, 입사 10년 이상된 직원들에게 배지가 부여되었습니다.", badgeName);
    }

    //도비는 자유에요 배지 활성화
    @Transactional
    public void activateDobbyIsFreeBadge() {
        String badgeName = "도비는 자유에요";
        BadgeMaster dobbyBadge = badgeMasterRepository.findByBadgeName(badgeName);

        if (dobbyBadge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        dobbyBadge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(dobbyBadge);

        badgeService.assignAnnualLeaveBadge();

        log.info("{} 배지가 활성화되었으며, 오늘 연차를 사용하는 직원들에게 배지가 부여되었습니다.", badgeName);
    }


    //워라밸 마스터 배지 활성화
    @Transactional
    public void activateWorkLifeBalanceBadge() {
        String badgeName = "워라벨 마스터";
        BadgeMaster workLifeBalanceBadge = badgeMasterRepository.findByBadgeName(badgeName);

        if (workLifeBalanceBadge == null) {
            throw new BadgeNotFoundException("배지를 찾을 수 없습니다: " + badgeName);
        }

        // 배지 상태를 Enabled로 설정
        workLifeBalanceBadge.setStatus(BadgeStatus.Enabled);
        badgeMasterRepository.save(workLifeBalanceBadge);

        badgeService.assignWorkLifeBalanceBadge();

        log.info("{} 배지가 활성화되었으며, 연차 사용률이 80% 이상인 직원들에게 배지가 부여되었습니다.", badgeName);
    }
}
