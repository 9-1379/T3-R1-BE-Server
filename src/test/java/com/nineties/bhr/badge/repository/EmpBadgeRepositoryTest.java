package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.emp.domain.Employees;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/init.sql")
class EmpBadgeRepositoryTest {

    @Autowired
    private EmpBadgeRepository empBadgeRepository;

    private Employees employees;
    private BadgeMaster badgeMaster;

    @Test
    void 배지가_있는지_확인() {
        // when
        boolean badgecheck = empBadgeRepository.badgeCheck(employees, badgeMaster, new Date());
        // then
//        assertTrue(badgecheck);
        assertFalse(badgecheck);
    }

    @Test
    void annualBadgeCheck() {
    }

    @Test
    void findByBadgeMaster() {
    }

    @Test
    void findCurrentBadgesByEmployeeAndDate() {
    }
}