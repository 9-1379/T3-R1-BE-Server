package com.nineties.bhr.annual.service;

import com.nineties.bhr.annual.domain.Annual;
import com.nineties.bhr.annual.domain.AnnualList;
import com.nineties.bhr.annual.repository.AnnualListRepository;
import com.nineties.bhr.annual.repository.MyAnnualRepository;
import com.nineties.bhr.emp.domain.Employees;
import com.nineties.bhr.emp.dto.EmployeeProfileProjection;
import com.nineties.bhr.emp.dto.EmployeeProjection;
import com.nineties.bhr.emp.repository.EmployeesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MyAnnualServiceTest {

    private MyAnnualRepository myAnnualRepository;
    private AnnualListRepository annualListRepository;
    private EmployeesRepository employeesRepository;
    private MyAnnualService myAnnualService;

    @BeforeEach
    void setUp() {
        // 가짜 리포지토리 생성
        myAnnualRepository = new MyAnnualRepositoryStub();
        annualListRepository = new AnnualListRepositoryStub();
        employeesRepository = new EmployeesRepositoryStub();

        // 직원 데이터 생성
        Employees employeeWithAnnual = new Employees("test_employee_id", "Test Employee");
        Employees employeeWithoutAnnual = new Employees("test_employee_id_without_annual", "Test Employee Without Annual");
        employeesRepository.save(employeeWithAnnual);
        employeesRepository.save(employeeWithoutAnnual);

        // 연차 데이터 생성
        Annual annual = new Annual();
        annual.setAnnualYear("2024");
        annual.setEmployees(employeeWithAnnual);
        annual.setAnnualTotal(15L);
        annual.setAnnualUsed(5L);
        myAnnualRepository.save(annual);

        // 서비스 생성
        myAnnualService = new MyAnnualService(myAnnualRepository, annualListRepository, employeesRepository);
    }

    @Test
    public void getMyAnnualDetailsByYearAndEmpId_WhenAnnualExists_ShouldReturnDetails() {
        // Given
        String annualYear = "2024";
        String empId = "test_employee_id";

        // When
        Map<String, Object> result = myAnnualService.getMyAnnualDetailsByYearAndEmpId(annualYear, empId);

        // Then
        Map<String, Object> expected = new HashMap<>();
        expected.put("annualYear", "2024");
        expected.put("empId", "test_employee_id");
        expected.put("annualTotal", 15L);
        expected.put("annualUsed", 5L);

        assertEquals(expected, result);
    }

    @Test
    public void getMyAnnualDetailsByYearAndEmpId_WhenAnnualDoesNotExist_ShouldReturnEmptyDetails() {
        // Given
        String annualYear = "2024";
        String empId = "test_employee_id_without_annual";

        // When
        Map<String, Object> result = myAnnualService.getMyAnnualDetailsByYearAndEmpId(annualYear, empId);

        // Then
        Map<String, Object> expected = new HashMap<>();

        assertEquals(expected, result);
    }

    // 가짜 MyAnnualRepository
    private static class MyAnnualRepositoryStub implements MyAnnualRepository {
        @Override
        public Optional<Annual> findByAnnualYearAndEmployees_Id(String annualYear, String empId) {
            // 가짜 데이터베이스에서 연차 조회
            if (empId.equals("test_employee_id")) {
                Annual annual = new Annual();
                annual.setAnnualYear("2024");
                annual.setEmployees(new Employees("test_employee_id", "Test Employee"));
                annual.setAnnualTotal(15L);
                annual.setAnnualUsed(5L);
                return Optional.of(annual);
            }
            return Optional.empty();
        }

        @Override
        public Annual save(Annual annual) {
            // 가짜 데이터베이스에 연차 저장
            return annual;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Annual> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Annual> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<Annual> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Annual getOne(String s) {
            return null;
        }

        @Override
        public Annual getById(String s) {
            return null;
        }

        @Override
        public Annual getReferenceById(String s) {
            return null;
        }

        @Override
        public <S extends Annual> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Annual> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Annual> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Annual> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Annual> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Annual> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Annual, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public <S extends Annual> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Annual> findById(String s) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public List<Annual> findAll() {
            return null;
        }

        @Override
        public List<Annual> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(String s) {

        }

        @Override
        public void delete(Annual entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends Annual> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<Annual> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Annual> findAll(Pageable pageable) {
            return null;
        }
    }

    // 가짜 AnnualListRepository
    private static class AnnualListRepositoryStub implements AnnualListRepository {
        @Override
        public List<AnnualList> findByEmployees_Username(String username) {
            return null;
        }

        @Override
        public int countByDateWithinAnnualLeave(Date today) {
            return 0;
        }

        @Override
        public List<Employees> findByDateWithinAnnualLeave(Date today) {
            return null;
        }

        @Override
        public List<AnnualList> findByStartDate(Date today) {
            return null;
        }

        @Override
        public List<AnnualList> findByStartDateBeforeAndEndDateAfterOrEndDateIsNull(Date startDate, Date endDate) {
            return null;
        }

        @Override
        public List<AnnualList> findByAnnualYearAndEmployees(String currentYear, Employees employee) {
            return null;
        }

        @Override
        public Long findAnnualCountByEmployeeAndYear(String empId, String annualYear) {
            // 가짜 데이터베이스에서 연차 개수 조회
            if (empId.equals("test_employee_id")) {
                return 5L;
            }
            return 0L;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends AnnualList> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends AnnualList> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<AnnualList> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> longs) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public AnnualList getOne(Long aLong) {
            return null;
        }

        @Override
        public AnnualList getById(Long aLong) {
            return null;
        }

        @Override
        public AnnualList getReferenceById(Long aLong) {
            return null;
        }

        @Override
        public <S extends AnnualList> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends AnnualList> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends AnnualList> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends AnnualList> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends AnnualList> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends AnnualList> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends AnnualList, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public <S extends AnnualList> S save(S entity) {
            return null;
        }

        @Override
        public <S extends AnnualList> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<AnnualList> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<AnnualList> findAll() {
            return null;
        }

        @Override
        public List<AnnualList> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(AnnualList entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends AnnualList> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<AnnualList> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<AnnualList> findAll(Pageable pageable) {
            return null;
        }
    }

    // 가짜 EmployeesRepository
    private static class EmployeesRepositoryStub implements EmployeesRepository {
        private final Map<String, Employees> employeesMap = new HashMap<>();

        @Override
        public Employees save(Employees employee) {
            // 가짜 데이터베이스에 직원 저장
            employeesMap.put(employee.getId(), employee);
            return employee;
        }

        @Override
        public Employees findById(String id) {
            // 가짜 데이터베이스에서 직원 조회
            return employeesMap.get(id);
        }

        @Override
        public boolean existsById(String s) {
            return false;
        }

        @Override
        public Employees findByUsername(String username) {
            return null;
        }

        @Override
        public EmployeeProfileProjection findEmpProfile(String username) {
            return null;
        }

        @Override
        public Boolean existsByUsername(String username) {
            return null;
        }

        @Override
        public Long findMaxEmpNo() {
            return null;
        }

        @Override
        public List<EmployeeProjection> findEmpNoNameDeptNameEmail() {
            return null;
        }

        @Override
        public <S extends Employees> List<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public List<Employees> findAll() {
            return null;
        }

        @Override
        public List<Employees> findAllById(Iterable<String> strings) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public List<Employees> findActiveEmployees() {
            return null;
        }

        @Override
        public long countActiveEmployees() {
            return 0;
        }

        @Override
        public List<Employees> findByHireDateBefore(Date cutoffDate) {
            return null;
        }

        @Override
        public void deleteById(String id) {
            // 가짜 데이터베이스에서 직원 삭제
            employeesMap.remove(id);
        }

        @Override
        public void delete(Employees entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends String> strings) {

        }

        @Override
        public void deleteAll(Iterable<? extends Employees> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Employees> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Employees> List<S> saveAllAndFlush(Iterable<S> entities) {
            return null;
        }

        @Override
        public void deleteAllInBatch(Iterable<Employees> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<String> strings) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Employees getOne(String s) {
            return null;
        }

        @Override
        public Employees getById(String s) {
            return null;
        }

        @Override
        public Employees getReferenceById(String s) {
            return null;
        }

        @Override
        public <S extends Employees> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Employees> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Employees> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public <S extends Employees> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Employees> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Employees> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Employees, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public List<Employees> findAll(Sort sort) {
            return null;
        }

        @Override
        public Page<Employees> findAll(Pageable pageable) {
            return null;
        }
    }
}
