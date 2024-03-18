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

    @Column
    private Long annualTotal;

    @Column
    private Long annualCount;

    @OneToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
