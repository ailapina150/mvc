package com.resume.request;

import com.resume.dto.EmployeeDto;
import com.resume.model.EnglishLevels;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    @NotBlank
    private String name;
    private String position;
    private String format;
    private String photo;
    @NotBlank
    @Email
    private String email;
    private String tg;
    private Long phone;
    private String summary;
    @NotBlank
    private EnglishLevels englishLevel;

    public EmployeeDto toDto() {
        return EmployeeDto
                .builder()
                .name(name)
                .position(position)
                .format(format)
                .photo(photo)
                .email(email)
                .phone(phone)
                .summary(summary)
                .tg(tg)
                .englishLevel(englishLevel)
                .build();
    }
}
