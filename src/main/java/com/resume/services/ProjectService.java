package com.resume.services;

import com.resume.annotations.SimpleLog;
import com.resume.dto.ProjectDto;
import com.resume.mappers.ProjectMapper;
import com.resume.model.Employee;
import com.resume.model.Project;
import com.resume.springdatarepositories.EmployeeRepository;
import com.resume.springdatarepositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@SimpleLog
public class ProjectService {
    private final ProjectRepository repository;
    private final EmployeeRepository employeeRepository;
    //private final ProjectMapper mapper;

    public ProjectDto getById(Integer id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));

        return project.toDto();
    }

    public List<ProjectDto> getAll() {
        List<Project> projects = repository.findAll();
        return projects.stream().map(Project::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ProjectDto save(ProjectDto dto, String developerName) {
        Employee employee = employeeRepository.findByName(developerName)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Project project = dto.toEntity();
        project.setEmployee(employee);
        var saved = repository.save(project);
        return saved.toDto();
    }

    @Transactional
    public ProjectDto save(ProjectDto dto, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Project project = dto.toEntity();
        project.setEmployee(employee);
        var saved = repository.save(project);
        return saved.toDto();
    }

    public void delete(Integer id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        repository.delete(project);
    }
}
