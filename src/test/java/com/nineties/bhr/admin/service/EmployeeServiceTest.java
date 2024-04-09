package com.nineties.bhr.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.nineties.bhr.admin.dto.EmployeeDTO;
import com.nineties.bhr.emp.domain.Address;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.domain.Status;
import com.nineties.bhr.emp.repository.DeptRepository;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeesRepository employeesRepository;

    @BeforeEach
    void setUp() {
        employeesRepository = mock(EmployeesRepository.class);
        employeeService = new EmployeeService(employeesRepository);
    }

    @Test
    void testFindAllEmployees() {
        // given
        List<Employees> employeesList = new ArrayList<>();
        employeesList.add(new Employees());
        when(employeesRepository.findAll()).thenReturn(employeesList);

        // when
        List<EmployeeDTO> result = employeeService.findAllEmployees();

        // then
        assertEquals(1, result.size());
    }

    @Test
    void testGetEmployeeById() {
        // given
        String id = "1";
        Employees employee = new Employees();
        when(employeesRepository.findById(id)).thenReturn(Optional.of(employee));

        // when
        EmployeeDTO result = employeeService.getEmployeeById(id);

        // then
        assertNotNull(result);
    }

    @Test
    void testGetEmployeeByIdNotFound() {
        // given
        String id = "1";
        when(employeesRepository.findById(id)).thenReturn(Optional.empty());

        // when
        EmployeeDTO result = employeeService.getEmployeeById(id);

        // then
        assertNull(result);
    }

    @Test
    void testRetireEmployee() {
        // given
        String employeeId = "1";
        Employees employee = new Employees();
        when(employeesRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // when
        employeeService.retireEmployee(employeeId);

        // then
        assertEquals(Status.LEAVE, employee.getStatus());
        verify(employeesRepository, times(1)).save(any());
    }

    @Test
    void testUpdateEmployee() {
        // given
        String id = "1";
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setName("John Doe");
        updatedEmployeeDTO.setGender("MALE");

        Employees existingEmployee = new Employees();
        existingEmployee.setId(id);
        when(employeesRepository.findById(id)).thenReturn(Optional.of(existingEmployee));

        // when
        EmployeeDTO result = employeeService.updateEmployee(id, updatedEmployeeDTO);

        // then
        assertNotNull(result);
        assertEquals(updatedEmployeeDTO.getName(), result.getName());
        verify(employeesRepository, times(1)).save(existingEmployee);
    }

    @Test
    void testUpdateEmployeeNotFound() {
        // given
        String id = "1";
        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();

        when(employeesRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(id, updatedEmployeeDTO));
        verify(employeesRepository, never()).save(any());
    }

    @Test
    void testRetireMultipleEmployees() {
        // given
        List<String> employeeIds = List.of("1", "2", "3");
        List<Employees> employeesToRetire = new ArrayList<>();
        for (String id : employeeIds) {
            Employees employee = new Employees();
            employee.setId(id);
            employeesToRetire.add(employee);
        }

        when(employeesRepository.findAllById(employeeIds)).thenReturn(employeesToRetire);

        // when
        employeeService.retireMultipleEmployees(employeeIds);

        // then
        for (Employees employee : employeesToRetire) {
            assertEquals(Status.LEAVE, employee.getStatus());
        }
        verify(employeesRepository, times(employeeIds.size())).save(any());
    }
}
