package com.nineties.bhr.join.dto;

import com.nineties.bhr.emp.domain.Address;
import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.domain.Role;
import com.nineties.bhr.emp.domain.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class JoinDTO {

    private String name;
    private Gender gender;
    private String birthday;
    private String phoneNumber;
    private String email;
    private String position;
    private String jobId;
    private Date hireDate;
    private Address address;
    private String username;
    private String password;
    private String deptName;
}
