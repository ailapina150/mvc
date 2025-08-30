package com.resume.services;

import com.resume.annotations.SimpleLog;
import com.resume.dto.EmployeeDto;
import com.resume.model.Employee;
import com.resume.jpaRepositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@SimpleLog
public class EmployeeService {
    private final EmployeeRepository repository;
   // private final EmployeeMapper mapper;

    @Cacheable(value = "employees", key = "#id")
    public EmployeeDto getById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));

        return employee.toDto(); //mapper.toDto(Employee);
    }

    @Cacheable(value = "allEmployees")
    public List<EmployeeDto> getAll() {
        List<Employee>employees = repository.findAll();
        return employees.stream().map(Employee::toDto).toList();//mapper.toDto(Employees);
    }

    @CacheEvict(value = "allEmployees", allEntries = true)
    @Transactional
    public EmployeeDto save(EmployeeDto dto) {
        var saved = repository.save(dto.toEntity());
        return saved.toDto();//mapper.toDto(saved);
    }

    @Caching(evict = {
            @CacheEvict(value = "employees", key = "#employee.id"),
            @CacheEvict(value = "allEmployees", allEntries = true)
    })
    public void delete(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        repository.delete(employee);
    }

    @Caching(evict = {
            @CacheEvict(value = "employees",  allEntries = true),
            @CacheEvict(value = "allEmployees", allEntries = true)
    })
    public void deleteAll(){
        repository.deleteAll();
    }
}
