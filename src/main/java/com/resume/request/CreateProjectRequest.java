package com.resume.request;

import com.resume.dto.ProjectDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    @Size(min = 1, max = 255)
    private String developerName;

    public ProjectDto toDto() {
        return ProjectDto
                .builder()
                .name(name)
                .description(description)
                .build();
    }
}
