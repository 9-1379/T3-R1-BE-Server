package com.nineties.bhr.badge.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/init.sql")
class BadgeMasterRepositoryTest {

    @Autowired
    private BadgeMasterRepository badgeMasterRepository;

    @Test
    void 모든_배지_목록_조회() {

    }

    @Test
    void 배지_이름으로_조회() {
    }

    @Test
    void 배지_이름과_상태로_조회() {
    }
}