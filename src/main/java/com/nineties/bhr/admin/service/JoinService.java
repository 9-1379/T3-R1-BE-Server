package com.nineties.bhr.admin.service;

import com.nineties.bhr.badge.service.BadgeService;
import com.nineties.bhr.emp.domain.Dept;
import com.nineties.bhr.emp.repository.DeptRepository;
import com.nineties.bhr.emp.domain.*;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import com.nineties.bhr.admin.dto.JoinDTO;
import com.nineties.bhr.admin.dto.JoinPageDTO;
import com.nineties.bhr.sequence.domain.SequenceTable;
import com.nineties.bhr.sequence.repository.SequenceTableRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.rmi.server.LogStream.log;

@Service
public class JoinService {

    private static final Logger log = LoggerFactory.getLogger(JoinService.class);
    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DeptRepository deptRepository;
    private final SequenceTableRespository sequenceTableRespository;

    private final BadgeService badgeService;

    public JoinService(EmployeesRepository employeesRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DeptRepository deptRepository, SequenceTableRespository sequenceTableRespository, BadgeService badgeService) {

        this.employeesRepository = employeesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.deptRepository = deptRepository;
        this.sequenceTableRespository = sequenceTableRespository;
        this.badgeService = badgeService;
    }

    public void joinProcess(JoinDTO joinDTO) throws Exception {

        String name = joinDTO.getName();
        Gender gender = joinDTO.getGender();
        Date birthday = joinDTO.getBirthday();
        String phoneNumber = joinDTO.getPhoneNumber();
        String email = joinDTO.getEmail();
        String position = joinDTO.getPosition();
        String jobId = joinDTO.getJobId();
        Date hireDate = joinDTO.getHireDate();
        Address address = joinDTO.getAddr();
        String username = joinDTO.getUsername();

        Boolean isUsernameExist = employeesRepository.existsByUsername(username);

        if (isUsernameExist) {
            throw new Exception("Username '" + username + "' already exists.");
        }

        String password = joinDTO.getPassword();
        Dept dept = deptRepository.findByDeptName(joinDTO.getDeptName());

        Employees data = new Employees();

        Long maxEmpNo = employeesRepository.findMaxEmpNo();
        if (maxEmpNo == null) {
            maxEmpNo = 1L; // empNo가 아직 없으면, 초기값으로 1 설정
        } else {
            maxEmpNo += 1; // 기존 최대값에서 1 증가
        }

        data.setEmpNo(maxEmpNo);
        data.setName(name);
        data.setGender(gender);
        data.setBirthday(birthday);
        data.setPhoneNumber(phoneNumber);
        data.setEmail(email);
        data.setPosition(position);
        data.setJobId(jobId);
        data.setDept(dept);
        data.setHireDate(hireDate);
        data.setAddress(address);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setAuthorization(Role.HRMANAGER);
        data.setStatus(Status.WORKING);
        data.setAuthorization(Role.EMPLOYEE);

        Employees emp = employeesRepository.save(data);
        log.info("신규 직원 생성 완료 : {}", emp.getUsername());

        badgeService.assignBadgesToNewEmployee(emp);
    }

    public JoinPageDTO showId() {
        JoinPageDTO joinPageDTO = new JoinPageDTO();

        Long maxEmpNo = employeesRepository.findMaxEmpNo();
        if (maxEmpNo == null) {
            maxEmpNo = 1L; // empNo가 아직 없으면, 초기값으로 1 설정
        } else {
            maxEmpNo += 1; // 기존 최대값에서 1 증가
        }

        joinPageDTO.setEmpNum(maxEmpNo);

        SequenceTable sequenceTable = sequenceTableRespository.findById("entity_sequence").orElseThrow();

        Long empId = sequenceTable.getNextVal();
        joinPageDTO.setEmpId("e" + empId);

        List<String> deptNames = deptRepository.findAllDeptNames();
        joinPageDTO.setDeptNames(deptNames);

        return joinPageDTO;
    }
}