
INSERT INTO sequence_table (sequence_name, next_val)
VALUES ('entity_sequence', 1)
ON DUPLICATE KEY UPDATE next_val = next_val;

INSERT INTO dept (dept_name) VALUES
                                 ('영업팀'),
                                 ('밤양갱팀'),
                                 ('개발팀'),
                                 ('인사팀'),
                                 ('고구마팀');

