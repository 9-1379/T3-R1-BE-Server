package com.nineties.bhr.admin.controller;


import com.nineties.bhr.admin.dto.JoinDTO;
import com.nineties.bhr.admin.dto.JoinPageDTO;
import com.nineties.bhr.admin.service.JoinService;
import com.nineties.bhr.badge.domain.EmpBadge;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @GetMapping("/join")
    public JoinPageDTO joinPage() {

        return joinService.showId();
    }

    @PostMapping("/join")
    public ResponseEntity<List<EmpBadge>> newEmployee (@RequestBody @Valid JoinDTO joinDTO, HttpServletResponse response ) {
        List<EmpBadge> badges = joinService.joinProcess(joinDTO);

        return ResponseEntity.ok(badges);

    }
}
