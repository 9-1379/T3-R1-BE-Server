package com.nineties.bhr.annual.mapper;

import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.dto.AnnualListDTO;
import org.springframework.stereotype.Component;

@Component
public class AnnualMapper {


    public AnnualListDTO annualListDTO(AnnualList annualList) {

        AnnualListDTO annualListDTO = new AnnualListDTO();
        annualListDTO.setId(annualList.getId());
        annualListDTO.setAnnualYear(annualList.getAnnualYear());
        annualListDTO.setStartDate(annualList.getStartDate());
        annualListDTO.setEndDate(annualList.getEndDate());
        return annualListDTO;
    }
}
