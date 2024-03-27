package com.nineties.bhr.join.service;

import com.nineties.bhr.dept.domain.Dept;
import com.nineties.bhr.dept.repository.DeptRepository;
import com.nineties.bhr.emp.domain.*;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import com.nineties.bhr.join.dto.JoinDTO;
import com.nineties.bhr.join.dto.JoinPageDTO;
import com.nineties.bhr.sequence.domain.SequenceTable;
import com.nineties.bhr.sequence.repository.SequenceTableRespository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JoinService {

    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DeptRepository deptRepository;
    private final SequenceTableRespository sequenceTableRespository;

    public JoinService(EmployeesRepository employeesRepository, BCryptPasswordEncoder bCryptPasswordEncoder, DeptRepository deptRepository, SequenceTableRespository sequenceTableRespository) {

        this.employeesRepository = employeesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.deptRepository = deptRepository;
        this.sequenceTableRespository = sequenceTableRespository;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String name = joinDTO.getName();
        Gender gender = joinDTO.getGender();
        String birthday = joinDTO.getBirthday();
        String phoneNumber = joinDTO.getPhoneNumber();
        String email = joinDTO.getEmail();
        String position = joinDTO.getPosition();
        String jobId = joinDTO.getJobId();
        Date hireDate = joinDTO.getHireDate();
        Address address = joinDTO.getAddr();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        Dept dept = deptRepository.findByDeptName(joinDTO.getDeptName());

        Boolean isExist = employeesRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

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

        employeesRepository.save(data);
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