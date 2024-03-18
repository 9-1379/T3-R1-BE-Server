package com.nineties.bhr.dept.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "DEPT")
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    //@ManyToOne
    @Column(name = "emp_id", nullable = false)
    private Long empId;
}
