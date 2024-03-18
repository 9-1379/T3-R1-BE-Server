package com.nineties.bhr.badge.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "BADGE_MASTER")
public class BadgeMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badgeId;

    @Column(name = "badge_name", length = 20, nullable = false)
    private String badgeName;

    @Column(name = "badge_description", length = 100, nullable = false)
    private String badgeDescription;

    @Column(name = "badge_standard", nullable = false)
    private Long badgeStandard;

    @Lob
    @Column(name = "badge_image", nullable = false)
    private byte[] badgeImage;

}