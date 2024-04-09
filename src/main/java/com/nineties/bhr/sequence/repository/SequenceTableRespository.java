package com.nineties.bhr.sequence.repository;

import com.nineties.bhr.sequence.domain.SequenceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceTableRespository extends JpaRepository<SequenceTable, String> {
    Optional<SequenceTable> findById(String id);
}
