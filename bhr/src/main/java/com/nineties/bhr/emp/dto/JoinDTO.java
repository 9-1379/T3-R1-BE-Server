package com.nineties.bhr.emp.dto;

import com.nineties.bhr.dept.domain.Dept;
import com.nineties.bhr.emp.domain.Address;
import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.domain.Role;
import com.nineties.bhr.emp.domain.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Setter
@Getter
public class JoinDTO {

    private Long empNo;
    private String name;
    private Gender gender;
    private String birthday;
    private String phoneNumber;
    private String position;
    private String jobId;
    private Date hireDate;
    private String username;
    private String password;
    private Status status = Status.WORKING;
}
