package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.repository.AnnualRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnualListService {
    private final AnnualRepository annualRepository;

    public AnnualListService(AnnualRepository annualRepository) {
        this.annualRepository = annualRepository;
    }

    public void annualSave(AnnualListDTO annualListDTO) {
        String annualYear = annualListDTO.getAnnualYear();
        Date startDate = annualListDTO.getStartDate();
        Date endDate = annualListDTO.getEndDate();

         annualRepository.save();
    }
}
