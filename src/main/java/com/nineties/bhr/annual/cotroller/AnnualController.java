package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.dto.AnnualListDTO;
import com.nineties.bhr.annual.service.AnnualListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    public List<AnnualListDTO> getAnnualList() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        List<AnnualListDTO> annualList = annualService.getlist(name);

        return annualList;
    }
    @PostMapping("/annualSave")
    public ResponseEntity<Object> annualSave(@RequestBody @Valid AnnualListDTO annualListDTO) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        annualService.newAnnual(annualListDTO, name);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/annualList/{id}")
    public ResponseEntity<String> deleteAnnual(@PathVariable Long id) {
        annualService.deleteAnnual(id);
        return ResponseEntity.ok("delete");
    }
}
