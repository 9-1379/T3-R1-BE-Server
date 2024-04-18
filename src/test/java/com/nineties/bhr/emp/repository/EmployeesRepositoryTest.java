package com.nineties.bhr.emp.repository;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.domain.Role;
import com.nineties.bhr.emp.domain.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql("/init.sql")
class EmployeesRepositoryTest {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Test
    public void 임직원_정보_저장() {
        //given
        Employees emp1 = new Employees();
        emp1.setName("aaa");
        emp1.setEmpNo(1L);
        emp1.setGender(Gender.FEMALE);
        emp1.setBirthday(new Date());
        emp1.setPhoneNumber("01042991435");
        emp1.setPosition("aaa");
        emp1.setJobId("aaa");
        emp1.setHireDate(new Date());
        emp1.setUsername("aaa");
        emp1.setPassword("aaa");
        emp1.setAuthorization(Role.EMPLOYEE);

        Employees emp2 = new Employees();
        emp2.setName("bbb");
        emp1.setEmpNo(2L);
        emp2.setGender(Gender.FEMALE);
        emp2.setBirthday(new Date());
        emp2.setPhoneNumber("01042991435");
        emp2.setPosition("bbb");
        emp2.setJobId("bbb");
        emp2.setHireDate(new Date());
        emp2.setUsername("bbb");
        emp2.setPassword("bbb");
        emp2.setStatus(Status.WORKING);
        emp2.setAuthorization(Role.EMPLOYEE);

        //when
        Employees emp = employeesRepository.save(emp1);
        Employees emp3 = employeesRepository.save(emp2);

        //then
        assertThat(emp.getName()).isEqualTo("aaa");
        assertThat(emp.getId()).isEqualTo("e1");

        assertThat(emp3.getName()).isEqualTo("bbb");
        assertThat(emp3.getId()).isEqualTo("e2");
    }
}