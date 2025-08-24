package com.resume.services.fileFabrica;


import com.resume.model.FileFormat;

public class FileGenerateFactory {
    public static FileGenerator getFileGenerator(FileFormat fileFormat) {
        return switch (fileFormat) {
            case EXCEL -> new XlsxFileGenerator();
            case DOCX -> new DocxFileGenerator();
        };
    }
}
