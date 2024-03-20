package com.nineties.bhr.badge.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class EmpBadge {
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

    //부여일
    @Temporal(TemporalType.DATE)
    private Date date;

    //종료일
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
