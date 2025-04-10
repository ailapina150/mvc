package com.resume.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Project {
    private Long id;
    private String name;
    private String description;
    private List<String> tasks;
}
