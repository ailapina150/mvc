package com.resume.services;

import com.resume.dto.EmployeeDto;
import com.resume.mappers.EmployeeMapper;
import com.resume.model.Employee;
import com.resume.springdatarepositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public EmployeeDto getById(Long id) {
        Employee Employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));

        return mapper.toDto(Employee);
    }

    public List<EmployeeDto> getAll() {
        List<Employee> Employees = repository.findAll();
        return mapper.toDto(Employees);
    }

    @Transactional
    public EmployeeDto save(EmployeeDto dto) {
        var saved = repository.save(dto.toEntity());
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        repository.delete(employee);
    }

}
