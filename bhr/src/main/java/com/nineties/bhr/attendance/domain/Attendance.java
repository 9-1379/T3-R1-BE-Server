package com.nineties.bhr.attendance.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ATTENDANCE")

public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "att_id")
    private Long attId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "date")
    private Date date;

    @Column(name = "time_in")
    private Date timeIn;

    @Column(name = "time_out")
    private Date timeOut;
}
