package com.nineties.bhr.emp.domain;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.badge.domain.BadgeGrantLog;
import com.nineties.bhr.badge.domain.EmpBadge;
import com.nineties.bhr.dept.domain.Dept;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(columnDefinition = "TEXT")
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

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;

    @OneToOne(mappedBy = "leaderId")
    private Dept depts;

    @OneToMany(mappedBy = "employees")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "employees")
    private List<AnnualList> annualLists = new ArrayList<>();

    @OneToOne(mappedBy = "employees")
    private Annual annual;

    @OneToOne(mappedBy = "employees")
    private EmpBadge empBadge;

    @OneToMany(mappedBy = "employees")
    private List<BadgeGrantLog> badgeGrantLogs = new ArrayList<>();
}
