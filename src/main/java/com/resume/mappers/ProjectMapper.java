package com.resume.mappers;

import com.resume.dto.ProjectDto;
import com.resume.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    ProjectDto toDto(Project project);

    List<ProjectDto> toDto(List<Project> projects);

    Project toProject(ProjectDto projectDto);

}
