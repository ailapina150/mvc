package com.resume.dto;

import com.resume.model.Project;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectDto {
    private Integer id;
    private String name;
    private String description;

    public Project toEntity() {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        return project;
    }
}
