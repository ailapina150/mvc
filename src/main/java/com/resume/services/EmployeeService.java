package com.resume.services;

import com.resume.annotations.SimpleLog;
import com.resume.dto.EmployeeDto;
import com.resume.model.Employee;
import com.resume.springdatarepositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
@SimpleLog
public class EmployeeService {
    private final EmployeeRepository repository;
   // private final EmployeeMapper mapper;

    public EmployeeDto getById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));

        return employee.toDto(); //mapper.toDto(Employee);
    }

    public List<EmployeeDto> getAll() {
        List<Employee>employees = repository.findAll();
        return employees.stream().map(Employee::toDto).toList();//mapper.toDto(Employees);
    }

    @Transactional
    public EmployeeDto save(EmployeeDto dto) {
        var saved = repository.save(dto.toEntity());
        return saved.toDto();//mapper.toDto(saved);
    }

    public void delete(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        repository.delete(employee);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

}
