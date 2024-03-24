package com.nineties.bhr.admin.mapper;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.admin.dto.AdminDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    //Employees 엔티티에서 AdminDTO 로 변환 메서드
    public AdminDTO adminDTO(Employees employees) {
        AdminDTO dto = new AdminDTO();
        dto.setName(employees.getName());
        dto.setEmpNo(employees.getEmpNo());
        return dto;
    }
}
