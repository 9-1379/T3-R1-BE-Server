package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.dto.AdminAnnualStatusDTO;
import com.nineties.bhr.annual.service.AdminAnnualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AdminAnnualController {

    private final AdminAnnualService adminAnnualService;

    @Autowired
    public AdminAnnualController(AdminAnnualService adminAnnualService) {
        this.adminAnnualService = adminAnnualService;
    }

    @PostMapping("/annualTotal")
    public ResponseEntity<Object> totalAnnualSave(@RequestBody AdminAnnualDTO adminAnnualDTO) {

            adminAnnualService.newTotalAnnual(adminAnnualDTO);

            return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/status/{annualYear}")
    public List<AdminAnnualStatusDTO> getStatus(@PathVariable("annualYear") String annualYear) {

        return adminAnnualService.empStatusAll(annualYear);
    }


}
