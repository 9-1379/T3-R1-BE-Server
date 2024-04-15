package com.nineties.bhr.admin.service;

import com.nineties.bhr.admin.dto.EmployeeDTO;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.domain.Role;
import com.nineties.bhr.emp.domain.Status;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeesRepository employeesRepository;

    @SneakyThrows
    @BeforeEach
    @Transactional
    void setup() {
        // 데이터베이스에 있는 모든 레코드 삭제
        employeesRepository.deleteAll();

        //given
        Employees emp1 = new Employees();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date birthdayDate = sdf.parse("19991122");
        emp1.setName("aaa");
        emp1.setEmpNo(1L); // 'emp_no' 필드 설정
        emp1.setGender(Gender.FEMALE);
        emp1.setBirthday(birthdayDate);
        emp1.setPhoneNumber("01042991435");
        emp1.setPosition("aaa");
        emp1.setJobId("aaa");
        emp1.setHireDate(new Date());
        emp1.setUsername("aaa");
        emp1.setPassword("aaa");
        emp1.setAuthorization(Role.EMPLOYEE);
        emp1.setStatus(Status.WORKING);
        employeesRepository.save(emp1); // 직원 객체 저장

        Employees emp2 = new Employees();
        emp2.setName("bbb");
        emp1.setEmpNo(2L);
        emp2.setGender(Gender.FEMALE);
        emp2.setBirthday(birthdayDate);
        emp2.setPhoneNumber("01042991435");
        emp2.setPosition("bbb");
        emp2.setJobId("bbb");
        emp2.setHireDate(new Date());
        emp2.setUsername("bbb");
        emp2.setPassword("bbb");
        emp2.setStatus(Status.WORKING);
        emp2.setAuthorization(Role.EMPLOYEE);
    }

    @Test
    public void findAllEmployees_ShouldReturnAllEmployees() {
        // When: 모든 직원을 조회
        List<EmployeeDTO> employees = employeeService.findAllEmployees();

        // Then: 저장된 직원의 수와 일치해야 합니다
        assertEquals(1, employees.size()); // DB에 저장된 직원 수와 일치
    }

    @Test
    public void getEmployeeById_ShouldReturnCorrectEmployee() {
        // Given: 특정 ID를 가진 직원
        Employees savedEmployee = employeesRepository.findAll().get(0);

        // When: 해당 ID로 직원 정보 조회
        EmployeeDTO employee = employeeService.getEmployeeById(savedEmployee.getId());

        // Then: 조회된 직원 정보가 요청된 ID와 일치
        assertNotNull(employee);
        assertEquals(savedEmployee.getId(), employee.getId());
    }

    @Test
    @Transactional  // 데이터의 일관성을 유지하기 위해 트랜잭션 사용
    public void retireEmployee_ShouldChangeStatusToLeave() {
        // Given: 활동 중인 직원
        Employees activeEmployee = employeesRepository.findAll().get(0);

        // When: 해당 직원을 은퇴 상태로 변경
        employeeService.retireEmployee(activeEmployee.getId());

        // Then: 해당 직원의 상태는 LEAVE로 변경
        Employees retiredEmployee = employeesRepository.findById(activeEmployee.getId()).orElse(null);
        assertNotNull(retiredEmployee);
        assertEquals(Status.LEAVE, retiredEmployee.getStatus());
    }

    @Test
    public void retireMultipleEmployees_ShouldChangeStatusForAll() {
        // Given: 활동 중인 모든 직원의 ID 가져오기
        List<String> employeeIds = employeesRepository.findAll().stream()
                .map(Employees::getId)
                .collect(Collectors.toList());

        // When: 주어진 모든 직원을 은퇴 상태로 변경
        employeeService.retireMultipleEmployees(employeeIds);

        // Then: 모든 직원의 상태가 LEAVE로 변경되었는지 확인
        List<Employees> retiredEmployees = employeesRepository.findAllById(employeeIds);
        assertTrue(retiredEmployees.stream().allMatch(e -> e.getStatus() == Status.LEAVE));
    }

}
