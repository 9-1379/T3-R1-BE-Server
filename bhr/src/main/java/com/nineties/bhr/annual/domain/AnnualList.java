package com.nineties.bhr.annual.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ANNUAL_LIST")
public class AnnualList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_list_id")
    private Integer annualListId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "annual_date")
    private Date annualDate;
}
