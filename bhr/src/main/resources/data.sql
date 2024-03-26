DROP TABLE IF EXISTS sequence_table;
CREATE TABLE sequence_table
(
    sequence_name VARCHAR(50) NOT NULL,
    next_val      BIGINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (sequence_name)
);
INSERT INTO sequence_table (sequence_name, next_val)
VALUES ('entity_sequence', 1) ON DUPLICATE KEY
    UPDATE next_val = next_val;













