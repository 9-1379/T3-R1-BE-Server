package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.service.AnnualListService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AnnualController {

    private final AnnualListService annualService;

    public AnnualController(AnnualListService annualService) {
        this.annualService = annualService;
    }


    @GetMapping("/annualList")
    public Map<String, Object> getAnnualList() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

//        System.out.println(name);
        Map<String, Object> result = new HashMap<>();
        List<AnnualListDTO> annualList = annualService.getlist(name);

        result.put("annualList", annualList);

        return result;
    }
    @PostMapping("/annualSave")
    public ResponseEntity<Object> annualSave(@RequestBody @Valid AnnualListDTO annualListDTO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        annualService.newAnnual(annualListDTO, name);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
