package com.nineties.bhr.admin.mapper;

import com.nineties.bhr.admin.dto.AdminDTO;
import com.nineties.bhr.admin.dto.AttendanceCountDto;
import com.nineties.bhr.attendance.domain.Attendance;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    //Employees 엔티티에서 AdminDTO 로 변환 메서드
    public AdminDTO adminDTO(Employees employees) {
        AdminDTO dto = new AdminDTO();
        dto.setName(employees.getName());
        dto.setEmpNo(employees.getEmpNo());
        dto.setDeptName(employees.getDept().getDeptName());

        return dto;
    }
//    public AttendanceDTO attendanceDTO(Attendance attendance) {
//        AttendanceDTO attendDTO = new AttendanceDTO();
//        attendDTO.setTimeIn(attendance.getTimeIn());
//
//        return attendDTO;
//    }

    public AttendanceCountDto attendanceCountDto(Attendance attendance) {
        AttendanceCountDto countDto = new AttendanceCountDto();
        countDto.setEarlyBirdsCount(attendance.getTimeIn().getTime());

        return countDto;
    }
}
