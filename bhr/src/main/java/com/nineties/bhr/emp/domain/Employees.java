package com.nineties.bhr.emp.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Entity
@Table
public class Employees {

    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long empNo;

    @Column(nullable = false)
    private String name;

    @Lob
    private Byte[] profile_picture;

    @Temporal(TemporalType.DATE)
    private Date hire_date;

    @ColumnDefault("1")
    private Status status;
}
