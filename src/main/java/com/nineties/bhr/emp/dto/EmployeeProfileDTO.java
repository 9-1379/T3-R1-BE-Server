package com.nineties.bhr.emp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.ast.SqlAstTranslator;

import java.util.Date;

@Getter
@Setter
public class EmployeeProfileDTO {

    private String id;
    private String name;
    private String deptName;
    private String position;
    private String introduction;
    private String profilePicture;
    private Date hireDate;
    private String jobId;
}
