package com.nineties.bhr.badge.dto;

import com.nineties.bhr.badge.domain.BadgeStatus;

public interface BadgeProjection {

    String getBadgeName();

    String getBadgeDetail();

    String getBadgeImage();

    BadgeStatus getStatus();
}
