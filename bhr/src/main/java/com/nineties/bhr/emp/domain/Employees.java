package com.nineties.bhr.emp.domain;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.badge.domain.EmpBadge;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
<<<<<<< HEAD
<<<<<<< HEAD
import org.hibernate.annotations.ColumnDefault;
=======
>>>>>>> dev
=======
import org.hibernate.annotations.ColumnDefault;
>>>>>>> 4993b8ed8dcd85890c1eeb7c5f9052e293159667
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Employees {

    //a+숫자
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(generator = "custom-sequence-gen")
    @GenericGenerator(
            name = "custom-sequence-gen",
            strategy = "com.nineties.bhr.emp.domain.CustomSequenceGenerator"
    )
    private String id;

    //숫자 자동 +1
    @Column(nullable = false)
    private Long empNo;

    @Column(name = "emp_name", nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String birthday;

    @Column(nullable = false)
    private String phoneNumber;

    @Email
    private String email;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String jobId;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hireDate;

    @Embedded
    private Address address;

    @Column
    private String profilePicturePath; // 프로필 사진 파일 경로를 저장할 새 필드

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated (EnumType.STRING)
    private Role authorization;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Dept dept;
    @OneToMany(mappedBy = "employees")
    private List<Attendance> attendances = new ArrayList<>();


    @OneToMany(mappedBy = "employees")
    private List<AnnualList> annualLists = new ArrayList<>();

    @OneToMany(mappedBy = "employees")
    private List<Annual> annuals = new ArrayList<>();

    @OneToMany(mappedBy = "employees")
    private List<EmpBadge> empBadges = new ArrayList<>();

    }