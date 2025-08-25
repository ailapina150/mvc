package com.resume.mappers;

import com.resume.dto.EmployeeDto;
import com.resume.dto.ProjectDto;
import com.resume.model.Employee;
import com.resume.model.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);
    List<EmployeeDto> toDto(List<Employee> employees);
}
