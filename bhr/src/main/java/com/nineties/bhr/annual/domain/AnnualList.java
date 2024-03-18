package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class AnnualList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_list_id")
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date annualDate;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
