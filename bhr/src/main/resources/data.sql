INSERT INTO dept (dept_name) VALUES
                                 ('영업팀'),
                                 ('밤양갱팀'),
                                 ('개발팀'),
                                 ('인사팀'),
                                 ('고구마');
/* SQL 오류 (1364): Field 'password' doesn't have a default value */
INSERT INTO employees (hire_date, emp_no, birthday, emp_id, emp_name, job_id, password, phone_number, position, username)
VALUES
    ('2024-03-22', 22, '22', '2', 'ss', '2', '123', '123', 'dd', 'dd');