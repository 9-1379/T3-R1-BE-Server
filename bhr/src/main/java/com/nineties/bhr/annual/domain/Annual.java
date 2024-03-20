package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Annual {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "annual_id")
    private Long id;

    //귀속 연도
    private String annualYear;

    //총 생성 연차
    private Long annualTotal;

    //선 사용 연차
    private Long annualUsed;

    @OneToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;
}
