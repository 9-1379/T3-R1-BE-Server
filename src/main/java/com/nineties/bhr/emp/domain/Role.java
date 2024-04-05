package com.nineties.bhr.emp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MANAGER("ROLE_MANAGER"), HRMANAGER("ROLE_HRMANAGER"), EMPLOYEE("ROLE_MANAGER");

    Role(String value){
        this.value = value;
    }

    private String value;

    public static Role getRole(String roleStr) {
        roleStr = roleStr.split("_")[1];
        for (Role role : values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        return null; // 또는 기본값 또는 예외 처리
    }
}
