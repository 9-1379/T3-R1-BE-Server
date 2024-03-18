package com.nineties.bhr.badge.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;

@Entity
@Table
public class BadgeGrantLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private BadgeMaster badgeMaster;
}
