package com.resume.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "id_project")
    private Project project;
}
