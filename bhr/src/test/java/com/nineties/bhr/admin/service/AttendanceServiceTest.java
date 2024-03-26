//package com.nineties.bhr.admin.service;
//
//import com.nineties.bhr.attendance.domain.Attendance;
//import com.nineties.bhr.emp.repository.EmployeesRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//class AttendanceServiceTest {
//
//    @Mock
//    private EmployeesRepository employeesRepository;
//
//    @InjectMocks
//    private AttendanceService attendanceService;
//
//    public AttendanceServiceTest() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindEarlyEmployees() {
//        // 현재 날짜와 시간 설정
//        LocalDate currentDate = LocalDate.of(2024, 3, 25);
//        LocalTime currentTime = LocalTime.of(8, 0); // 8시
//
//        // 모의 객체로 리턴할 출근 정보 목록 생성
//        List<Attendance> mockAttendanceList = new ArrayList<>();
//        mockAttendanceList.add(new Attendance("John", LocalDate.of(2024, 3, 25), LocalTime.of(8, 30))); // 8시 30분 출근
//        mockAttendanceList.add(new Attendance("Alice", LocalDate.of(2024, 3, 25), LocalTime.of(7, 45))); // 7시 45분 출근
//        mockAttendanceList.add(new Attendance("Bob", LocalDate.of(2024, 3, 25), LocalTime.of(9, 15))); // 9시 15분 출근
//
//        // employeesRepository.findByStartDateAndTimeInBefore() 메서드가 호출될 때 모의 객체가 리턴할 값을 설정
//        when(employeesRepository.findByStartDateAndTimeInBefore(currentDate, currentTime)).thenReturn(mockAttendanceList);
//
//        // 테스트할 메서드 호출
//        List<Attendance> earlyEmployees = attendanceService.findEarlyEmployees();
//
//        // 예상되는 결과와 실제 결과를 비교
//        assertEquals(2, earlyEmployees.size()); // 9시 이전 출근한 직원은 2명
//        assertEquals("John", earlyEmployees.get(0).getName());
//        assertEquals("Alice", earlyEmployees.get(1).getName());
//    }
//}