package com.nineties.bhr.join.dto;

import com.nineties.bhr.dept.domain.Dept;
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
