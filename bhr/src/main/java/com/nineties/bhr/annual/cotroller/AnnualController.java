package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.service.AnnualListService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnualController {

    private final AnnualListService annualService;

    public AnnualController(AnnualListService annualService) {
        this.annualService = annualService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> annualSave(@RequestBody @Valid AnnualListDTO annualListDTO, HttpServletResponse response) {
        annualService.annualSave(annualListDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
