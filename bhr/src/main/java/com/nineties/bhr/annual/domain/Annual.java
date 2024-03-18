package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;

@Entity
@Table
public class Annual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_id")
    private Long id;

    @Column(columnDefinition = "int default 15")
    private Long annualTotal;

    @Column(nullable = true)
    private Long annualCount;

    @OneToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
