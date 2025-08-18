package com.resume.fabrica;


public class FileGenerateFactory {
    public static FileGenerator getFileGenerator(FileFormat fileFormat) {
        return switch (fileFormat) {
            case EXCEL -> new XlsxFileGenerator();
            case DOCX -> new DocxFileGenerator();
        };
    }
}
