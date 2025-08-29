package com.resume.model;
import com.resume.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "employees", schema ="public" )
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
    private String format;

    private String photo;

    private String email;
    private String tg;
    private Long phone;

    private String summary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Project> projects;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(name = "id_employee"))
    private List<Skills> skill;

    @Column(name ="english_level")
    @Enumerated(EnumType.ORDINAL)
    private EnglishLevels englishLevel;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Education> educations;

    public EmployeeDto toDto() {
        return EmployeeDto.builder()
                .id(id)
                .name(name)
                .position(position)
                .format(format)
                .photo(photo)
                .email(email)
                .tg(tg)
                .phone(phone)
                .summary(summary)
                .englishLevel(englishLevel)
                .build();
    }
}
