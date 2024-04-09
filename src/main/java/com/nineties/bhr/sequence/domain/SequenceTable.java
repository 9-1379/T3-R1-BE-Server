package com.nineties.bhr.sequence.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;



@Entity
@Data
public class SequenceTable {
    @Id
    @Column(name="sequence_name")
    String id;

    @Column(columnDefinition="bigint default 1")
    Long nextVal;
}
