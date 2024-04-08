package com.nineties.bhr.badge.domain;

import com.nineties.bhr.annual.domain.AnnualList;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class BadgeMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @Column(nullable = false)
    private String badgeName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String badgeDetail;

    @Lob
//    @Column(nullable = false)
    private String badgeImage;

    @Enumerated (EnumType.STRING)
    private BadgeStatus status;

    @OneToMany(mappedBy = "badgeMaster")
    private List<EmpBadge> empBadges = new ArrayList<>();

}