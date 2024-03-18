package com.nineties.bhr.badge.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "EMP_BADGE")
public class EmpBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_badge_id")
    private Long empBadgeId;

    //@ManyToOne
    @Column(name = "emp_id", nullable = false)
    private Long employee;

    @Column(name = "vacation_count", nullable = false)
    private Long vacationCount;

    @Column(name = "overtime_count", nullable = false)
    private Long overtimeCount;
}
