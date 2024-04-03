package com.nineties.bhr.annual.cotroller;

import com.nineties.bhr.annual.dto.AdminAnnualDTO;
import com.nineties.bhr.annual.service.AdminAnnualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
