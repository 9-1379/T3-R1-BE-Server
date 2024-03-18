package com.nineties.bhr.annual.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "ANNUAL")
public class Annual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_id")
    private Long annualId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "annual_total", columnDefinition = "int default 15")
    private Long annualTotal;

    @Column(name = "annual_count", nullable = true)
    private Long annualCount;
    }
