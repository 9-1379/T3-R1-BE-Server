package com.nineties.bhr.hrcard.service;

import com.nineties.bhr.emp.repository.EmployeesRepository;
import com.nineties.bhr.hrcard.dto.EmpListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HrCardService {
    private final EmployeesRepository employeesRepository;

    public HrCardService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<EmpListDTO> showEmpList() {
        List<EmpListDTO> empListDTOS = employeesRepository.findAllforlist();
        return empListDTOS;
    }
}
