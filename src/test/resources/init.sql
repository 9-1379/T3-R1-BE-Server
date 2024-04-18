MERGE INTO sequence_table USING (VALUES('entity_sequence', 1))
    AS vals(sequence_name, next_val)
    ON sequence_table.sequence_name = vals.sequence_name
    WHEN MATCHED THEN UPDATE SET sequence_table.next_val = sequence_table.next_val
    WHEN NOT MATCHED THEN INSERT (sequence_name, next_val) VALUES (vals.sequence_name, vals.next_val);


INSERT INTO dept (dept_name)
VALUES ('영업1팀'),
       ('재무팀'),
       ('개발팀'),
       ('인사팀'),
       ('영업2팀');

-- INSERT INTO employees (hire_date, dept_id, emp_no, birthday, emp_id, emp_name, job_id, password, phone_number, position,
--                        username, emp_authorization, emp_gender, emp_status)
-- VALUES ('2024-03-22', 1, 1, '1991-03-08', 'e1', '관리자', '관리자',
--         '$2a$10$GG6NfaD2UmNnDWQW8u6NtOftvo7m8fuxem8gZ2.Jm6z/QLbIwXF8m', '000-1234-1234', '관리자', 'admin', 'manager', 'MALE', 'WORKING'),
--        ('2024-03-23', 2, 2, '1991-03-10', 'e2', '김철수', '124', '1234', '000-1234-1234', '과장', 'test2', 'manager', 'MALE', 'WORKING'),
--        ('2024-03-24', 3, 3, '1991-03-15', 'e3', '밤양갱', '125', '1234', '000-1234-1234', '부장', 'test3', 'manager', 'MALE', 'WORKING'),
--        ('2024-03-24', 3, 4, '1991-03-15', 'e4', '이영진', '125', '1234', '000-1234-1234', '부장', 'test4', 'manager', 'MALE', 'WORKING');

insert into badge_master (badge_detail, badge_name, status, badge_image) values
('1995~2008년 출생자', 'Z세대', 'Enabled', null),
('1980~1994년 출생자', 'Y세대', 'Enabled', null),
('1964~1979년 출생자', 'X세대', 'Enabled', null),
('입사 후 6개월 이내', '귀여운건 나도 알아', 'Enabled', null),
('전 주 야근 3번 이상 (야근 기준 : 당일 PM 7시~ 익일 AM 6시)', '회사 지박령', 'Enabled', null),
('입사일로부터 10년', '전설속의 그대', 'Enabled', null),
('한해 연차 80% 사용', '워라벨 마스터', 'Enabled', null),
('연차 발생 시 배지 부여', '도비는 자유에요', 'Enabled', null);

