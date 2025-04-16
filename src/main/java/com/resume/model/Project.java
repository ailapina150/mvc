package com.resume.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@Entity
@Table(name = "projects", schema ="public" )
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private Long id;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_employee")
    private Employee employee;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
