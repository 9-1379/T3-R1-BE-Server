package com.nineties.bhr.join.controller;

import com.nineties.bhr.join.dto.JoinDTO;
import com.nineties.bhr.join.service.JoinService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<Object> newEmployee ( @RequestBody @Valid JoinDTO joinDTO, HttpServletResponse response ) {

        joinService.joinProcess(joinDTO);

        return ResponseEntity
                .status(HttpStatus.OK).build();  // 200(OK)를 응답 상태 코드로 지정
    }
}
