INSERT INTO sequence_table (sequence_name, next_val)
VALUES ('entity_sequence', 1)
ON DUPLICATE KEY UPDATE next_val = next_val;

INSERT INTO dept (dept_name) VALUES
                                ('영업팀'),
                                ('밤양갱팀'),
                                ('개발팀'),
                               ('인사팀'),
                               ('고구마팀');

-- INSERT INTO employees (hire_date, emp_no, birthday, emp_id, emp_name, job_id, password, phone_number, position,
--                        username)
-- -- VALUES ('2024-03-22',  1, '1991-03-08', '1', '이동혁', '123', '1234', '000-1234-1234', '대리', 'test1'),
-- --        ('2024-03-23',  2, '1991-03-10', '2', '김철수', '124', '1234', '000-1234-1234', '과장', 'test2'),
-- --        ('2024-03-24',  3, '1991-03-15', '3', '밤양갱', '125', '1234', '000-1234-1234', '부장', 'test3'),
-- --        ('2024-03-24',  4, '1991-03-15', '4', '이영진', '125', '1234', '000-1234-1234', '부장', 'test4');

INSERT INTO employees (hire_date, dept_id, emp_no, birthday, emp_id, emp_name, job_id, password, phone_number, position, username) VALUES
('2024-03-22', 1, 1, '1991-03-08', '1', '이동혁', '123', '1234', '000-1234-1234', '대리', 'test1'),
('2024-03-23', 2, 2, '1991-03-10', '2', '김철수', '124', '1234', '000-1234-1234', '과장', 'test2'),
('2024-03-24', 3, 3, '1991-03-15', '3', '밤양갱', '125', '1234', '000-1234-1234', '부장', 'test3'),
('2024-03-24', 3, 4, '1991-03-15', '4', '이영진', '125', '1234', '000-1234-1234', '부장', 'test4');
