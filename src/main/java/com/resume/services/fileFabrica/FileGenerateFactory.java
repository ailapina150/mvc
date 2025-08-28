package com.resume.services.fileFabrica;


import com.resume.annotations.FileMaker;
import com.resume.model.FileFormat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FileGenerateFactory {

    private XlsxFileGenerator xlsxFileGenerator;
    private DocxFileGenerator docxFileGenerator;

    public FileGenerator getFileGenerator(FileFormat fileFormat) {
        return switch (fileFormat) {
            case EXCEL -> xlsxFileGenerator;
            case DOCX -> docxFileGenerator;
        };
    }
}
