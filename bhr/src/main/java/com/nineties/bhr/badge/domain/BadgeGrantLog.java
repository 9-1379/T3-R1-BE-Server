package com.nineties.bhr.badge.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "BADGE_GRANT_LOG")
public class BadgeGrantLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    //@ManyToOne
    @Column(name = "emp_id", nullable = false)
    private Long empid;

    @ManyToOne
    @JoinColumn(name = "badge_id", nullable = false)
    private BadgeMaster badge;

}
