package com.nineties.bhr.annual.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@IdClass(AnnualPK.class)
public class Annual {
    //귀속 연도
    @Id
    private String annualYear;

    @Id @OneToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;

    //총 생성 연차
    private Long annualTotal;

    //선 사용 연차
    private Long annualUsed;


}
