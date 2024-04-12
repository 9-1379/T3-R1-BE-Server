package com.nineties.bhr.emp.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.ast.SqlAstTranslator;

@Getter
@Setter
public class EmployeeProfileDTO {

    private String id;
    private String name;
    private String deptName;
    private String position;
    private String introduction;
    private String profilePicture;
}
