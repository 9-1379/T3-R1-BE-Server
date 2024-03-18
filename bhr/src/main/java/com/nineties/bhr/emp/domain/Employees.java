package com.nineties.bhr.emp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

import static com.nineties.bhr.emp.domain.Status.WORKING;

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

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Long age;

    @Column(nullable = false)
    private String phoneNumber;

    @Email
    private String email;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hire_date;

    @Embedded
    private Address address;

    @Lob
    private Byte[] profilePicture;

    private String introduction;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("'WORKING'")
    @Enumerated (EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Role authorization;
}
