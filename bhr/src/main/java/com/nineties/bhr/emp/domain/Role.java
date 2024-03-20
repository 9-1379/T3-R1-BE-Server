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
}
