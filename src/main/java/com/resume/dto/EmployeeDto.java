package com.resume.dto;

import com.resume.model.Employee;
import com.resume.model.EnglishLevels;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String name;
    private String position;
    private String format;

    private String photo;

    private String email;
    private String tg;
    private Long phone;

    private String summary;

    private EnglishLevels englishLevel;

    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setName(this.name);
        employee.setPosition(this.position);
        employee.setFormat(this.format);
        employee.setPhoto(this.photo);
        employee.setEmail(this.email);
        employee.setTg(this.tg);
        employee.setPhone(this.phone);
        employee.setSummary(this.summary);
        employee.setEnglishLevel(this.englishLevel);
        return employee;
    }
}
