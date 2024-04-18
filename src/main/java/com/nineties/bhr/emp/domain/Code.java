package com.nineties.bhr.emp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Code {

    @Id @Column(name = "code_id")
    private String id;

    private String name;
}
