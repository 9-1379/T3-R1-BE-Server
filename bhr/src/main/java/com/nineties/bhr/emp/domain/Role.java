package com.nineties.bhr.emp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    MANAGER, HRMANAGER, EMPLOYEE;
}
