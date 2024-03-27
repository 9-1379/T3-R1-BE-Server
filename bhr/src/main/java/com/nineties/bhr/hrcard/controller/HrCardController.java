package com.nineties.bhr.hrcard.controller;

import com.nineties.bhr.hrcard.dto.EmpListDTO;
import com.nineties.bhr.hrcard.service.HrCardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HrCardController {

    private final HrCardService hrCardService;

    public HrCardController(HrCardService hrCardService) {
        this.hrCardService = hrCardService;
    }

    @GetMapping("/emp/hrCard")
    public List<EmpListDTO> ShowEmpList() {
        return hrCardService.showEmpList();
    }
}
