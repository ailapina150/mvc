package com.resume.services;

import com.resume.dto.ProjectDto;
import com.resume.mappers.ProjectMapper;
import com.resume.model.Project;
import com.resume.springdatarepositories.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServices {
    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    public ProjectDto getProjectById(Integer id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id));
        System.out.println(project);
        System.out.println(mapper.toDto(project));
        return mapper.toDto(project);
    }

    public List<ProjectDto> getAllProjects() {
        List<Project> projects = repository.findAll();
        projects.forEach(System.out::println);
        return mapper.toDto(projects);
    }
}
