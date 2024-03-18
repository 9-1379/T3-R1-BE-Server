package com.nineties.bhr.badge.domain;

import com.nineties.bhr.annual.domain.AnnualList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class BadgeMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @Column(nullable = false)
    private String badgeName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String badgeDescription;

    @Column(nullable = false)
    private Long badgeStandard;

    @Lob
    @Column(nullable = false)
    private byte[] badgeImage;

    @OneToMany(mappedBy = "badgeMaster")
    private List<BadgeGrantLog> badgeGrantLogs = new ArrayList<>();

}