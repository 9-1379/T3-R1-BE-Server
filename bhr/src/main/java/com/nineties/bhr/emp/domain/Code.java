package com.nineties.bhr.emp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Code {

    @Id @Column(name = "code_id")
    private String id;

    private String name;
}
