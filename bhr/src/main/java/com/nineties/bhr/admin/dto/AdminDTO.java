package com.nineties.bhr.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDTO {
    //관리자페이지에서 불러올 DTO생성
    private Long empNo;
    private String name;
    private String deptName;

}