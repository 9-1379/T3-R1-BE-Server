package com.nineties.bhr.login.dto;

public class RoleInfo {
    private String empId;
    private String role;

    public RoleInfo(String role) {

        this.role = role;
        this.empId = empId;
    }

    // Getter and Setter
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}