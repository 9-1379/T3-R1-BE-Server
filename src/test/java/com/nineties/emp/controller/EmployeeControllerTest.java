package com.nineties.emp.controller;

import com.nineties.bhr.admin.controller.EmployeeController;
import com.nineties.bhr.admin.dto.EmployeeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllEmployees() {
        // 테스트용 직원 목록 생성
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO("1", 123456L, "John Doe", "Male", "1990-01-01", "123-456-7890", "john@example.com", "Manager", "MGR", null, "john", "ACTIVE", "ADMIN", "Introduction", null, "Human Resources"),
                new EmployeeDTO("2", 654321L, "Jane Smith", "Female", "1995-06-15", "987-654-3210", "jane@example.com", "Developer", "DEV", null, "jane", "ACTIVE", "USER", "Introduction", null, "Engineering")
        );

        // 서비스 메소드 호출 시 반환할 값 설정
        when(employeeService.findAllEmployees()).thenReturn(employees);

        // 컨트롤러 메소드 호출
        List<EmployeeDTO> result = employeeController.getAllEmployees();

        // 결과 확인
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    // 다른 테스트 케이스에 대한 테스트도 작성할 수 있습니다.
}
