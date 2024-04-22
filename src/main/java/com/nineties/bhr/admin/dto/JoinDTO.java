package com.nineties.bhr.admin.dto;

import com.nineties.bhr.emp.domain.Address;
import com.nineties.bhr.emp.domain.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class JoinDTO {

    @NotBlank(message = "이름은 필수 사항입니다")
    private String name;
    @NotBlank(message = "성별은 필수 사항입니다")
    private Gender gender;
    @NotBlank(message = "생년월일은 필수 사항입니다")
    private Date birthday;
    @NotBlank(message = "전화번호는 필수 사항입니다")
    private String phoneNumber;

    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;
    @NotBlank(message = "직위는 필수 사항입니다")
    private String position;
    @NotBlank(message = "직무는 필수 사항입니다")
    private String jobId;
    @NotBlank(message = "입사일은 필수 사항입니다")
    private Date hireDate;
    private Address addr;
    @NotBlank(message = "아이디는 필수 사항입니다")
    private String username;
    @NotBlank(message = "비밀번호는 필수 사항입니다")
    private String password;
    private String deptName;
}
