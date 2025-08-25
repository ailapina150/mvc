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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
