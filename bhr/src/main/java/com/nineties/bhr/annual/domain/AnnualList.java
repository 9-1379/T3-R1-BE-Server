package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AnnualList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_list_id")
    private Long id;

    private String annualYear;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    //사용 개수
    private Long annualCnt;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
