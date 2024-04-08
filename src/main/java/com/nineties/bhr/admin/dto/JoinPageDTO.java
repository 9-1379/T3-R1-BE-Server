package com.nineties.bhr.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JoinPageDTO {
    String empId;
    Long empNum;
    List<String> deptNames;
}
