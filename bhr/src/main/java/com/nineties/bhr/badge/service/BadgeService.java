package com.nineties.bhr.badge.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.annual.repository.AnnualRepository;
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
import java.time.Instant;
import java.time.LocalDate;
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
    private final AnnualRepository annualRepository;

    public BadgeService(BadgeMasterRepository badgeMasterRepository, EmpBadgeRepository empBadgeRepository, AttendanceRepository attendanceRepository, EmployeesRepository employeesRepository, AnnualListRepository annualListRepository, AnnualRepository annualRepository) {
        this.badgeMasterRepository = badgeMasterRepository;
        this.empBadgeRepository = empBadgeRepository;
        this.attendanceRepository = attendanceRepository;
        this.employeesRepository = employeesRepository;
        this.annualListRepository = annualListRepository;
        this.annualRepository = annualRepository;
    }

    public void assignBadgesToNewEmployee(Employees employee) {
        assignGenerationBadge(employee);
        assignNewbieBadge(employee);
    }

    //세대별 배지 부여
    public void assignGenerationBadge(Employees employee) {
        // 현재 날짜 기준으로 세대별 배지 결정
        String generationBadgeName = determineGenerationBadge(employee.getBirthday());

        // "세대" 배지가 활성화 상태인지 확인합니다.
        BadgeMaster generationBadge = badgeMasterRepository.findByBadgeNameAndStatus(generationBadgeName, BadgeStatus.Enabled);

        if(generationBadge != null) {
            assignBadge(employee, generationBadge, null);
        }
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

    // 신입 사원 배지 부여 (입사 후 6개월 동안 유효)
    private void assignNewbieBadge(Employees employee) {

        LocalDate hireDate = employee.getHireDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate badgeEndDate = hireDate.plusMonths(6); // 종료일은 입사일로부터 6개월 후

        // "귀여운건 나도 알아" 배지가 활성화 상태인지 확인합니다.
        BadgeMaster newbieBadge = badgeMasterRepository.findByBadgeNameAndStatus("귀여운건 나도 알아", BadgeStatus.Enabled);

        if(newbieBadge != null) {
            assignBadge(employee, newbieBadge, badgeEndDate);
        }
    }

    // 매주 일요일 자정에 실행하여 "회사 지박령" 배지 부여
    @Scheduled(cron = "0 0 0 * * SUN")
    public void assignOvertimeBadge() {
        LocalDate today = LocalDate.now();
        // 지난 주의 월요일 (주의 시작)
        LocalDate startOfWeek = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // 지난 주의 일요일 (주의 종료)
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        // LocalDate를 Date로 변환합니다.
        Date startOfWeekDate = java.sql.Date.valueOf(startOfWeek);
        Date endOfWeekDate = java.sql.Date.valueOf(endOfWeek);

        // "회사 지박령" 배지가 활성화 상태인지 확인합니다.
        BadgeMaster overtimeBadge = badgeMasterRepository.findByBadgeNameAndStatus("회사 지박령", BadgeStatus.Enabled);
        if (overtimeBadge != null) {
            // 지난 주 야근 조건을 충족하는 직원의 ID 리스트를 조회합니다.
            List<String> employeeIds = attendanceRepository.findEmployeesWithOvertimeLastWeek(startOfWeekDate, endOfWeekDate);
            for (String empId : employeeIds) {
                // 문자열 ID를 사용하여 직원 엔티티를 조회합니다.
                Employees employee = employeesRepository.findById(empId).orElse(null);
                if (employee != null) {
                    assignBadge(employee, overtimeBadge, endOfWeek);
                }
            }
        }
    }

    // 매일 자정에 실행하여 "전설속의 그대" 배지 부여
    @Scheduled(cron = "0 0 0 * * *")
    public void assignDecadeBadge() {
        LocalDate tenYearsAgo = LocalDate.now().minusYears(10);
        BadgeMaster decadeBadge = badgeMasterRepository.findByBadgeNameAndStatus("전설속의 그대", BadgeStatus.Enabled);

        if (decadeBadge != null) {
            List<Employees> employeesList = employeesRepository.findByHireDateBefore(java.sql.Date.valueOf(tenYearsAgo));
            for (Employees employee : employeesList) {
                boolean alreadyHasValidBadge = empBadgeRepository.existsByEmployeesAndBadgeMasterAndEndDateAfterOrEndDateIsNull(employee, decadeBadge, new Date());
                if (!alreadyHasValidBadge) {
                    // 배지의 종료일은 설정하지 않습니다 (무기한으로 유효)
                    assignBadge(employee, decadeBadge, null);
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
            //오늘 연차 중인 직원 찾기
            List<AnnualList> activeAnnualLists = annualListRepository.findByStartDateBeforeAndEndDateAfterOrEndDateIsNull(todayDate);
            for (AnnualList annualList : activeAnnualLists) {
                Employees employee = annualList.getEmployees();
                // annualListStartDate와 annualListEndDate를 직접 Date 타입으로 사용합니다.
                Date annualListStartDate = annualList.getStartDate(); // 이미 Date 타입
                Date annualListEndDate = annualList.getEndDate();
                // 현재 시간 이전, 연차 시작일 이후, 배지 종료일이 오늘 이후인 경우에 이미 배지가 존재하는지 확인
                boolean alreadyAssigned = empBadgeRepository.existsByEmployeesAndBadgeMasterAndDateAfterAndDateBeforeAndEndDateAfter(employee, leaveBadge, annualListStartDate, todayDate, todayDate);
                if (!alreadyAssigned) {
                    // 해당 기간 동안 유효한 배지가 없으면 새로운 배지를 부여
                    assignBadge(employee, leaveBadge, convertToLocalDate(annualListEndDate));
                }
            }
        }
    }

    // 워라벨 마스터 배지
    public void workLifeBalanceBadge(Employees employee) {
        LocalDate today = LocalDate.now();
        String currentYear = String.valueOf(today.getYear());

        //배지 활성화 확인
        BadgeMaster workLifeBadge = badgeMasterRepository.findByBadgeNameAndStatus("워라벨 마스터", BadgeStatus.Enabled);

        if (workLifeBadge != null) {

            boolean alreadyHasBadge = empBadgeRepository.existsByEmployeesAndBadgeMasterAndEndDateAfterOrIsNull(employee, workLifeBadge, java.sql.Date.valueOf(today));

            if (!alreadyHasBadge) {
                // 이번 년도 연차 확인
                Annual annualData = annualRepository.findByAnnualYearAndEmployees(currentYear, employee);

                if (annualData != null) {

                    long totalAnnualUsed = annualData.getAnnualUsed(); // 선사용 연차
                    long totalAnnualGenerated = annualData.getAnnualTotal(); // 총 생성 연차

                    // 올해 사용된 연차 개수 계산
                    List<AnnualList> annualListsThisYear = annualListRepository.findByAnnualYearAndEmployees(currentYear, employee);
                    long annualUsedThisYear = annualListsThisYear.stream().mapToLong(AnnualList::getAnnualCnt).sum();
                    totalAnnualUsed += annualUsedThisYear; // 올해 사용된 연차를 선사용 연차에 추가

                    // 연차 사용률 계산
                    double usageRate = (double) totalAnnualUsed / totalAnnualGenerated;

                    if (usageRate >= 0.8) {
                        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
                        assignBadge(employee, workLifeBadge, endOfYear); // 연차 사용률이 80% 이상일 경우 배지 부여

                    }
                }
            }
        }
    }



    private LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    private void assignBadge(Employees employee, BadgeMaster badge, LocalDate endDate) {

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

