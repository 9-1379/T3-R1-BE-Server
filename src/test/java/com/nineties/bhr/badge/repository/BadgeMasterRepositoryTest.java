package com.nineties.bhr.badge.repository;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.domain.BadgeStatus;
import com.nineties.bhr.badge.dto.BadgeProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/init.sql")
class BadgeMasterRepositoryTest {
    @Autowired
    private BadgeMasterRepository badgeMasterRepository;

    private BadgeProjection badgeProjection;

    @Test
    void 모든_배지목록_조회() {
        // given init.sql로 대체

        // when
        List<BadgeProjection> badgeProjections = badgeMasterRepository.findAllProjectedBy();

        // then
        // 모든 목록은 size 테스트
        assertThat(badgeProjections.size()).isEqualTo(8);
//        assertThat(badgeProjections).isNotNull();
    }

    @Test
    void 배지이름으로_조회() {
        // 배지이름으로 테스트 했을시 해당 배지가 나와야함
        // given init.sql로 대체

        // when
        BadgeMaster badgeMaster = badgeMasterRepository.findByBadgeName("귀여운건 나도 알아");

        // then
        assertThat(badgeMaster.getBadgeName()).isEqualTo("귀여운건 나도 알아");
    }

    @Test
    void 배지이름과_상태로_조회() {
        // when
        BadgeMaster badgeMaster = badgeMasterRepository.findByBadgeNameAndStatus("귀여운건 나도 알아", BadgeStatus.Enabled);

        // then
        assertThat(badgeMaster.getBadgeName()).isEqualTo("귀여운건 나도 알아");
        assertThat(badgeMaster.getStatus()).isEqualTo(BadgeStatus.Enabled);
    }
}