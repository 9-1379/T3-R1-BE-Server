package com.nineties.bhr.badge.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;

@Entity
@Table(name = "EMP_BADGE")
public class EmpBadge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_badge_id")
    private Long id;

    @Column(nullable = false)
    private Long vacationCount;

    @Column(nullable = false)
    private Long overtimeCount;

    @OneToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
