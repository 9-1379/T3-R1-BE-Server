package com.nineties.bhr.badge.service;

import com.nineties.bhr.badge.domain.BadgeMaster;
import com.nineties.bhr.badge.dto.BadgeProjection;
import com.nineties.bhr.badge.repository.BadgeMasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBadgeService {

    private final BadgeMasterRepository badgeMasterRepository;

    public AdminBadgeService(BadgeMasterRepository badgeMasterRepository) {
        this.badgeMasterRepository = badgeMasterRepository;
    }

    public List<BadgeProjection> showBadgeList() {
        return badgeMasterRepository.findAllProjectedBy();
    }
}
