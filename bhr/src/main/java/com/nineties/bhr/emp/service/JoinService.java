package com.nineties.bhr.emp.service;

import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.domain.Gender;
import com.nineties.bhr.emp.domain.Role;
import com.nineties.bhr.emp.domain.Status;
import com.nineties.bhr.emp.dto.JoinDTO;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JoinService {

    private final EmployeesRepository employeesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(EmployeesRepository employeesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.employeesRepository = employeesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        Long empNo = joinDTO.getEmpNo();
        String name = joinDTO.getName();
        Gender gender = joinDTO.getGender();
        String birthday = joinDTO.getBirthday();
        String phoneNumber = joinDTO.getPhoneNumber();
        String position = joinDTO.getPosition();
        String jobId = joinDTO.getJobId();
        Date hireDate = joinDTO.getHireDate();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        Status status = joinDTO.getStatus();

        Boolean isExist = employeesRepository.existsByUsername(username);

        if (isExist) {

            return;
        }

        Employees data = new Employees();

        data.setEmpNo(empNo);
        data.setName(name);
        data.setGender(gender);
        data.setBirthday(birthday);
        data.setPhoneNumber(phoneNumber);
        data.setPosition(position);
        data.setJobId(jobId);
        data.setHireDate(hireDate);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setAuthorization(Role.HRMANAGER);
        data.setStatus(status);

        employeesRepository.save(data);
    }
}