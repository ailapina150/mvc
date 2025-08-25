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

    public void setId(Long id) {
        this.id = id;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public void setEnglishLevel(EnglishLevels englishLevel) {
        this.englishLevel = englishLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getFormat() {
        return format;
    }

    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getTg() {
        return tg;
    }

    public Long getPhone() {
        return phone;
    }

    public String getSummary() {
        return summary;
    }

    public EnglishLevels getEnglishLevel() {
        return englishLevel;
    }
}
