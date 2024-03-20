package com.nineties.bhr.dept.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Dept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private Long id;

    @Column(nullable = false)
    private String deptName;

    @OneToOne
    @JoinColumn(name = "leader_id")
    private Employees leaderId;

    @OneToMany
    private List<Employees> employees = new ArrayList<>();
}
