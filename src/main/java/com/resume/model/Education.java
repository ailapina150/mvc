package com.resume.model;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name = "educations", schema ="public" )
public class Education {
    @Id
    private Integer id;

    @Column(name="year_start")
    private Integer yearStart;

    @Column(name ="year_end")
    private Integer yearEnd;

    private String university;

    private String degree;

    @ManyToOne()
    @JoinColumn(name = "id_employee")
    private Employee employee;
}
