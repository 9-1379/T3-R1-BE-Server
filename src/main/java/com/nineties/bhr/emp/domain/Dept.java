package com.nineties.bhr.emp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany (mappedBy = "dept")
    private List<Employees> employees = new ArrayList<>();
}
