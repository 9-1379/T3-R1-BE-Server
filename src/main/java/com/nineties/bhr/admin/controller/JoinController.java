package com.nineties.bhr.admin.controller;


import com.nineties.bhr.admin.dto.JoinDTO;
import com.nineties.bhr.admin.dto.JoinPageDTO;
import com.nineties.bhr.admin.service.JoinService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @GetMapping("/join/new")
    public JoinPageDTO joinPage() {
        return joinService.showId();
    }

    @PostMapping("/join")
    public ResponseEntity<Object> newEmployee (@Valid @RequestBody JoinDTO joinDTO) {

        try {
            joinService.joinProcess(joinDTO);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
        return ResponseEntity.ok(Collections.singletonMap("message", "New employee has been successfully added."));  // 200(OK)를 응답 상태 코드로 지정
    }
}
