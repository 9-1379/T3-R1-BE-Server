package com.nineties.bhr.badge.service;

import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.attendance.repository.AttendanceRepository;
import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.BadgeStatus;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BadgeService {

    private static final Logger log = LoggerFactory.getLogger(BadgeService.class);
    private final BadgeMasterRepository badgeMasterRepository;
    private final EmpBadgeRepository empBadgeRepository;
    private final AttendanceRepository attendanceRepository;
    private final EmployeesRepository employeesRepository;
    private final AnnualListRepository annualListRepository;

    public BadgeService(BadgeMasterRepository badgeMasterRepository, EmpBadgeRepository empBadgeRepository, AttendanceRepository attendanceRepository, EmployeesRepository employeesRepository, AnnualListRepository annualListRepository) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.empBadgeRepository = empBadgeRepository;
        this.attendanceRepository = attendanceRepository;
        this.employeesRepository = employeesRepository;
        this.annualListRepository = annualListRepository;
    }

    public void assignBadgesToNewEmployee(Employees employee) {
        // 현재 날짜 기준으로 세대별 배지 결정
        String generationBadgeName = determineGenerationBadge(employee.getBirthday());
        // 세대별 배지에는 종료일 없음
        assignBadge(employee, generationBadgeName, null);

        // 신입 사원 배지 부여 (입사 후 6개월 동안 유효)
        LocalDate hireDate = employee.getHireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate badgeEndDate = hireDate.plusMonths(6); // 종료일은 입사일로부터 6개월 후
        assignBadge(employee, "귀여운건 나도 알아", badgeEndDate);
    }

    private String determineGenerationBadge(Date birthday) {
        // 여기서 직원의 생년을 기반으로 세대를 결정하는 로직 구현
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int year = cal.get(Calendar.YEAR);

        if (year >= 1995) {
            return "Z세대";
        } else if (year >= 1980) {
            return "Y세대";
        } else if (year >= 1964) {
            return "X세대";
        }
        return null; // 기본값, 혹은 예외 처리
    }

    // 매주 일요일 자정에 실행하여 "회사 지박령" 배지 부여
    @Scheduled(cron = "0 0 0 * * SUN")
    public void assignOvertimeBadge() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).atStartOfDay();
        LocalDateTime endOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atTime(23, 59, 59);
        LocalDate badgeEndDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)); // 배지 종료일은 다음 주 일요일

        List<String> employeeIds = attendanceRepository.findEmployeesWithOvertimeLastWeek(startOfWeek, endOfWeek);
        for (String empId : employeeIds) {
            Employees employee = employeesRepository.findById(empId).orElse(null);
            if (employee != null) {
                // "회사 지박령" 배지 부여, 종료일은 다음 주 일요일
                assignBadge(employee, "회사 지박령", badgeEndDate);
            }
        }
    }

    // 매일 자정에 실행하여 "전설속의 그대" 배지 부여
    @Scheduled(cron = "0 0 0 * * *")
    public void assignDecadeBadge() {
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        // "전설속의 그대" 배지가 활성화 상태인지 확인
        BadgeMaster decadeBadge = badgeMasterRepository.findByBadgeNameAndStatus("전설속의 그대", BadgeStatus.Enabled);

        if (decadeBadge != null) {
            // 배지가 활성화되어 있으면, 입사 10년이 넘은 직원을 찾는다.
            List<Employees> employeesList = employeesRepository.findByHireDateBefore(tenYearsAgo);
            for (Employees employee : employeesList) {
                // 이미 "전설속의 그대" 배지를 받았는지 확인
                boolean alreadyHasBadge = empBadgeRepository.existsByEmployeesAndBadgeMaster(employee, decadeBadge);
                if (!alreadyHasBadge) {
                    // 해당 직원에게 "전설속의 그대" 배지 부여
                    assignBadge(employee, "전설속의 그대", null);
                }
            }
        }
    }

    // 매일 자정에 실행하여 "도비는 자유에요" 배지 부여
    @Scheduled(cron = "0 0 0 * * *")
    public void assignAnnualLeaveBadge() {
        LocalDate today = LocalDate.now();
        Date todayDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        BadgeMaster leaveBadge = badgeMasterRepository.findByBadgeNameAndStatus("도비는 자유에요", BadgeStatus.Enabled);

        if (leaveBadge != null) {
            List<AnnualList> annualLists = annualListRepository.findByStartDate(today);
            for (AnnualList annualList : annualLists) {
                Employees employee = annualList.getEmployees();
                // 오늘 날짜에 해당 배지가 이미 부여되었는지 확인
                boolean alreadyAssignedToday = empBadgeRepository.existsByEmployeesAndBadgeMasterAndDate(employee, leaveBadge, todayDate);
                if (!alreadyAssignedToday) {
                    // 오늘 날짜에 해당 배지가 부여되지 않았다면, 다시 부여
                    assignBadge(employee, "도비는 자유에요", today.plusDays(1)); // 연차 다음 날까지 유효
                }
            }
        }
    }

    // Date를 LocalDate로 변환하는 도우미 메서드
    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void assignBadge(Employees employee, String badgeName, LocalDate endDate) {
        BadgeMaster badge = badgeMasterRepository.findByBadgeNameAndStatus(badgeName, BadgeStatus.Enabled);
        if (badge != null) {
            EmpBadge empBadge = new EmpBadge();
            empBadge.setEmployees(employee);
            empBadge.setBadgeMaster(badge);
            empBadge.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (endDate != null) {
                empBadge.setEndDate(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
            empBadgeRepository.save(empBadge);
            log.info("{} 사원에게 {} 배지 부여", employee.getName(), badge.getBadgeName());
        }
    }
}

