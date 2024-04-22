package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.badge.dto.MyBadgeDTO;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmpBadgeService {

    public final EmpBadgeRepository empBadgeRepository;
    public final EmployeesRepository employeesRepository;

    public EmpBadgeService(EmpBadgeRepository empBadgeRepository, EmployeesRepository employeesRepository) {
        this.empBadgeRepository = empBadgeRepository;
        this.employeesRepository = employeesRepository;
    }

    public List<MyBadgeDTO> getRecentBadgesForCurrentUser(String username) {
        Employees emp = employeesRepository.findByUsername(username);

        List<EmpBadge> allBadges = empBadgeRepository.findCurrentBadgesByEmployeeAndDate(emp.getId(), new Date());

        // 배지를 badge_id 기준으로 중복 제거하고 가장 최근 것만 유지
        allBadges = allBadges.stream()
                .sorted(Comparator.comparing(EmpBadge::getDate).reversed()) // 최신 날짜 우선으로 정렬
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                badge -> badge.getBadgeMaster().getId(), // badge_id로 구별
                                badge -> badge,
                                (existing, replacement) -> existing, // 이미 있는 경우 현재 항목 유지
                                LinkedHashMap::new),
                        map -> new ArrayList<>(map.values())
                ))
                .stream()
                .limit(3) // 최대 3개까지
                .collect(Collectors.toList());

        List<MyBadgeDTO> dtos = new ArrayList<>();

        for(EmpBadge badges : allBadges) {
            MyBadgeDTO dto = new MyBadgeDTO();
            dto.setBadgeId(badges.getBadgeMaster().getId());
            dto.setBadgeName(badges.getBadgeMaster().getBadgeName());
            dto.setBadgeImage(badges.getBadgeMaster().getBadgeImage());

            dtos.add(dto);
        }

        return dtos;
    }

    public List<MyBadgeDTO> getBadgesForHrCard(String id) {
        Employees emp = employeesRepository.findById(id).orElseThrow();
        return getRecentBadgesForCurrentUser(emp.getUsername());
    }
}
