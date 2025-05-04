package com.resume.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "educations", schema ="public" )
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", yearStart=" + yearStart +
                ", yearEnd=" + yearEnd +
                ", university='" + university + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}
