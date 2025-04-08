package com.resume.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Education {
    private Integer id;
    private Integer yearStart;
    private Integer yearEnd;
    private String university;
    private String degree;
}
