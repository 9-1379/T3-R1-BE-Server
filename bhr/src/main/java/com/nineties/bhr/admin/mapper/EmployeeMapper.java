package com.nineties.bhr.admin.mapper;

import com.nineties.bhr.admin.dto.AdminDTO;
import com.nineties.bhr.emp.domain.Employees;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    //Employees 엔티티에서 AdminDTO 로 변환 메서드
    public AdminDTO adminDTO(Employees employees) {
        AdminDTO dto = new AdminDTO();
        dto.setName(employees.getName());
        dto.setEmpNo(employees.getEmpNo());
        dto.setDeptName(employees.getDept().getDeptName());;

        return dto;

//        Dept deptName = null;
//        // 부서 이름 설정
//        if (employees.getDept() != null) {
//            Dept deptName = employeesRepository.findDeptNameById(employees.getDept().getId());
//        }
//        dto.setDeptName(deptName);


    }
}
