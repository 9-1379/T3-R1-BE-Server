package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.dto.AdminAnnualStatusDTO;
import com.nineties.bhr.annual.repository.AnnualRepository;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.emp.domain.Dept;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminAnnualService {

    private final AnnualRepository annualRepository;
    private final EmployeesRepository employeesRepository;
    private final AnnualListRepository annualListRepository;

    @Autowired
    public AdminAnnualService(AnnualRepository annualRepository, EmployeesRepository employeesRepository, AnnualListRepository annualListRepository) {

        this.employeesRepository = employeesRepository;
        this.annualListRepository = annualListRepository;
        this.annualRepository = annualRepository;
    }

    public void newTotalAnnual(AdminAnnualDTO adminAnnualDTO) {

        List<Employees> employeesList = employeesRepository.findAll();
        for (int i = 0; i < employeesList.size(); i++) {
            Employees employees = employeesList.get(i);

            Optional<Annual> existingAnnual = annualRepository.findByAnnualYearAndEmployees(adminAnnualDTO.getAnnualYear(), employees);

            if (existingAnnual.isPresent()) {
                Annual annual = existingAnnual.get();
                if (annual.getAnnualTotal() == null || annual.getAnnualTotal() <= 0) {
                    annual.setAnnualTotal(adminAnnualDTO.getAnnualTotal());
                    annualRepository.save(annual); // 변경된 값으로 업데이트
                }
            } else {
                Annual annual = new Annual();
                annual.setAnnualYear(adminAnnualDTO.getAnnualYear());
                annual.setEmployees(employees);
                annual.setAnnualTotal(adminAnnualDTO.getAnnualTotal());
                annual.setAnnualUsed(0L);
                annualRepository.save(annual);
        }
    }

        public List<AdminAnnualStatusDTO> empStatusAll (String annualYear){

            List<Annual> employeesList = annualRepository.findByAnnualYear(annualYear);
            List<AdminAnnualStatusDTO> statusDTOList = new ArrayList<>();


            for (int i = 0; i < employeesList.size(); i++) {
                Annual annual = employeesList.get(i);
                Employees employees = annual.getEmployees();

                Long cnt = annualListRepository.findAnnualCountByEmployeeAndYear(employees.getId(), annualYear);

                AdminAnnualStatusDTO statusDTO = new AdminAnnualStatusDTO();
                statusDTO.setName(employees.getName());
                statusDTO.setAnnualTotal(annual.getAnnualTotal());
                statusDTO.setAnnualCnt(cnt);
                statusDTO.setAnnualUsed(annual.getAnnualUsed());
                statusDTO.setDeptName(employees.getDept().getDeptName());

                statusDTOList.add(statusDTO);
            }
            return statusDTOList;
        }
    }
