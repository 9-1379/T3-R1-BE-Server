package com.nineties.bhr.emp.domain;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.jdbc.Work;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomSequenceGenerator implements IdentifierGenerator {

    private static final String SEQUENCE_PREFIX = "e"; // ID 접두사

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        final String sequenceName = "entity_sequence"; // 사용할 시퀀스 이름
        final Long[] nextVal = new Long[1];

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try (PreparedStatement ps = connection.prepareStatement(
                        "SELECT next_val FROM sequence_table WHERE sequence_name = ? FOR UPDATE")) {
                    ps.setString(1, sequenceName);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new HibernateException("Could not retrieve sequence value for sequence name: " + sequenceName);
                        }
                        nextVal[0] = rs.getLong(1);
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement(
                        "UPDATE sequence_table SET next_val = ? WHERE sequence_name = ?")) {
                    ps.setLong(1, nextVal[0] + 1);
                    ps.setString(2, sequenceName);
                    ps.executeUpdate();
                }
            }
        });

        if (nextVal[0] == null) {
            throw new HibernateException("Failed to generate sequence value");
        }

        // 생성된 시퀀스 값에 접두사를 붙여서 ID 반환
        return SEQUENCE_PREFIX + nextVal[0];
    }
}
