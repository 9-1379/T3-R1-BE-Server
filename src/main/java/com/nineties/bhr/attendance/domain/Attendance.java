package com.nineties.bhr.attendance.domain;

import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "att_id")
    private Long id;

    //출근일
    @Temporal(TemporalType.DATE)
    private Date startDate;

    //퇴근일
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Temporal(TemporalType.TIME)
    private Date timeIn;

    @Temporal(TemporalType.TIME)
    private Date timeOut;

    @ManyToOne
    @JoinColumn(name = "emp_id")
    private Employees employees;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // PRESENT, LATE, ABSENT, ON_LEAVE
}
