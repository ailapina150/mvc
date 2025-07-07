package com.resume.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProjectRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    @Size(min = 1, max = 255)
    private String developerName;
}
