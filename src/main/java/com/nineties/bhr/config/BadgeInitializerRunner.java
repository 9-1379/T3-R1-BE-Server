package com.nineties.bhr.config;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BadgeInitializerRunner implements CommandLineRunner {

    @Autowired
    private BadgeMasterRepository badgeMasterRepository;

    @Override
    public void run(String... args) throws Exception {
        initializeBadgeImages();
    }

    private void initializeBadgeImages() {
        updateBadgeImage("Z세대", "/badge/z.png");
        updateBadgeImage("Y세대", "/badge/y.png");
        updateBadgeImage("X세대", "/badge/x.png");
        updateBadgeImage("귀여운건 나도 알아", "/badge/cute.jpg");
        updateBadgeImage("회사 지박령", "/badge/night_shift.jpg");
        updateBadgeImage("전설속의 그대", "/badge/legend.jpg");
        updateBadgeImage("워라벨 마스터", "/badge/work_and_life_balance.jpg");
        updateBadgeImage("도비는 자유에요", "/badge/dobby_is_free.jpg");
    }

    private void updateBadgeImage(String badgeName, String imagePath) {
        BadgeMaster badge = badgeMasterRepository.findByBadgeName(badgeName);

        //배지가 존재하지 않을 경우 새로 생성
        if (badge == null) {
            badge = new BadgeMaster();
            badge.setBadgeName(badgeName);
        }

        //이미지 경로 업데이트
        badge.setBadgeImage(imagePath); //'badgeImage' 필드에 이미지 경로를 저장
        badgeMasterRepository.save(badge);
    }
}
