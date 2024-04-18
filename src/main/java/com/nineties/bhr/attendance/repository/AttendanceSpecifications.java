package com.nineties.bhr.attendance.repository;

import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceSpecifications {
    public static Specification<Attendance> buildSpecification(String name, Date date) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Attendance, Employees> employeeJoin = root.join("employees", JoinType.INNER);

            if (name != null && !name.isEmpty()) {
                // 이름, 부서, 직위, 직무에 대한 검색
                Predicate namePredicate = criteriaBuilder.like(employeeJoin.get("name"), "%" + name + "%");
                Predicate deptPredicate = criteriaBuilder.like(employeeJoin.get("dept").get("deptName"), "%" + name + "%");
                Predicate positionPredicate = criteriaBuilder.like(employeeJoin.get("position"), "%" + name + "%");
                Predicate jobIdPredicate = criteriaBuilder.like(employeeJoin.get("jobId"), "%" + name + "%");

                predicates.add(criteriaBuilder.or(namePredicate, deptPredicate, positionPredicate, jobIdPredicate));
            }

            if (date != null) {
                // 날짜에 대한 검색
                predicates.add(criteriaBuilder.equal(root.get("startDate"), date));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
