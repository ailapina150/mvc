package com.resume.mappers;

import com.resume.dto.ProjectDto;
import com.resume.model.Project;
import org.mapstruct.Mapper;
import lombok.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    List<ProjectDto> toDto(List<Project> projects);
}
