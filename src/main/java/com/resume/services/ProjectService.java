package com.resume.services;

import com.resume.dto.ProjectDto;
import com.resume.mappers.ProjectMapper;
import com.resume.model.Employee;
import com.resume.model.Project;
import com.resume.request.CreateProjectRequest;
import com.resume.springdatarepositories.EmployeeRepository;
import com.resume.springdatarepositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper mapper;

    public ProjectDto getProjectById(Integer id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));

        return mapper.toDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        List<Project> projects = repository.findAll();
        return mapper.toDto(projects);
    }

    @Transactional
    public ProjectDto createProject(CreateProjectRequest request) {
        Employee employee = employeeRepository.findByName(request.getDeveloperName())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .employee(employee)
                .build();
        var  saved = repository.save(project);
        return mapper.toDto(saved);
    }

    public void deleteProject(Integer id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));
        repository.delete(project);
    }
}
