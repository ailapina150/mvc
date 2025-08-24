package com.resume.services.fileFabrica;

import com.resume.model.Employee;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XlsxFileGenerator implements FileGenerator {
    @Override
    public String generateFile(Employee employee) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("Resume");

        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(employee.getName());

        Row row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("Phone");
        row2.createCell(1).setCellValue(employee.getPhone());

        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("Email");
        row3.createCell(1).setCellValue(employee.getEmail());

        Row row4 = sheet.createRow(4);
        row4.createCell(0).setCellValue("Telegram");
        row4.createCell(1).setCellValue(employee.getTg());

        try {
            String fileName = FOLDER_PATH + employee.getId() + ".xlsx";
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
