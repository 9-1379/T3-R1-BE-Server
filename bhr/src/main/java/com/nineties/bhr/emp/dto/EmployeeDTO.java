package com.nineties.bhr.emp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private String id;
    private Long empNo;
    private String name;
    private String gender;
    private String birthday;
    private String phoneNumber;
    private String email;
    private String position;
    private String jobId;
    private Date hireDate;
    private String username;
    private String status;
    private String authorization;
    private String introduction;
    private String deptName; // 간단히 부서 이름만 포함

    // 생성자, getter 및 setter
    // Lombok을 사용하지 않는 경우 직접 작성 필요
}
