INSERT INTO dept (dept_name) VALUES
                                 ('영업팀'),
                                 ('밤양갱팀'),
                                 ('개발팀'),
                                 ('인사팀'),
                                 ('고구마');
/* SQL 오류 (1364): Field 'password' doesn't have a default value */
INSERT INTO employees (hire_date, emp_no, birthday, emp_id, emp_name, job_id, password, phone_number, position, username)
VALUES
    ('2024-03-22', 22, '1991-03-08', '33', '이동혁', '123', '1234', '000-1234-1234', '대리', 'test1'),
    ('2024-03-23', 22, '1991-03-10', '44', '김철수', '124', '1234', '000-1234-1234', '과장', 'test2'),
    ('2024-03-24', 22, '1991-03-15', '55', '밤양갱', '125', '1234', '000-1234-1234', '부장', 'test3');