package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.mapper.AnnualMapper;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnualListService {
    private final AnnualListRepository annualListRepository;
    private final EmployeesRepository employeesRepository;
    private final AnnualMapper annualMapper;

    @Autowired
    public AnnualListService(AnnualListRepository annualListRepository, EmployeesRepository employeesRepository, AnnualMapper annualMapper) {
        this.annualListRepository = annualListRepository;
        this.employeesRepository = employeesRepository;
        this.annualMapper = annualMapper;
    }

    public long calculateDaysDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public void newAnnual(AnnualListDTO annualListDTO, String username) {
        AnnualList annual = new AnnualList();
        annual.setAnnualYear(annualListDTO.getAnnualYear());
        annual.setStartDate(annualListDTO.getStartDate());
        annual.setEndDate(annualListDTO.getEndDate());

        Employees employees = new Employees();
        employees = employeesRepository.findByUsername(username);
        annual.setEmployees(employees);

        // Calculate days difference
        LocalDate startDate = annualListDTO.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = annualListDTO.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long daysDifference = calculateDaysDifference(startDate, endDate) + 1;
        annual.setAnnualCnt(daysDifference);

        annualListRepository.save(annual);
    }
    public List<AnnualListDTO> getlist(String name) {

        return annualListRepository.findByEmployees_Username(name).stream()
                .map(annualMapper::annualListDTO)
                .collect(Collectors.toList());
    }
}