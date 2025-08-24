package com.resume.request;

import com.resume.model.FileFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendFileRequest {
    @NotBlank
    @Email
    @Schema(description = "Email адрес для отправки файла",
            example = "ailapina150@gmail.com",
            defaultValue = "ailapina150@gmail.com")
    private String emailAddress = "ailapina150@gmail.com";
    @NotBlank
    @Schema(description = "Формат файла",
            example = "DOCX",
            defaultValue = "DOCX")
    private FileFormat fileFormat = FileFormat.DOCX;
}
