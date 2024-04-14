package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.repository.AnnualRepository;
import com.nineties.bhr.badge.repository.EmpBadgeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // h2 데이터베이스를 사용하여 단위테스트 진행
@AutoConfigureTestDatabase(replace = Replace.ANY)
@ExtendWith(SpringExtension.class)//여기에 서비스?

class AdminAnnualServiceTest {

    @Autowired
    private AdminAnnualService adminAnnualService;

    @Autowired
    private EmpBadgeRepository empBadgeRepository;

    @Autowired
    private AnnualRepository annualRepository;

    @Test
    void newTotalAnnual() {
        // given
        AdminAnnualDTO adminAnnualDTO = new AdminAnnualDTO();
        adminAnnualDTO.setAnnualYear("2024");
        adminAnnualDTO.setAnnualTotal(10L);
        // when
        adminAnnualService.newTotalAnnual(adminAnnualDTO);
        // then
        List<Annual> annuals = annualRepository.findAll();
        assertThat(annuals).isNotEmpty();
        assertThat(annuals.size()).isEqualTo(empBadgeRepository.count());
    }

    @Test
    void empStatusAll() {
    }
}