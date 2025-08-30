package com.resume.services.fileFabrica;

import com.resume.annotations.FileMaker;
import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.services.FileService;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class DocxFileGenerator implements FileGenerator {

    @Override
    @FileMaker( fileFormat = FileFormat.DOCX)
   // @Cacheable(value = CACHE_DOCX_NAME, key = "#employee.id")
    public String generateFile(EmployeeDto employee) {
        return null;
    }
//        XWPFDocument doc = new XWPFDocument();
//        XWPFParagraph titleParagraph = doc.createParagraph();
//        titleParagraph.setAlignment(ParagraphAlignment.CENTER); // Выравнивание по центру
//
//        XWPFRun titleRun = titleParagraph.createRun();
//        titleRun.setText("Resume");
//        titleRun.addBreak();
//        titleRun.setText(employee.getName());
//
//        titleRun.setBold(true);      // Жирный
//        titleRun.setFontFamily("Times New Roman"); // Шрифт
//        titleRun.setFontSize(14);    // Размер шрифта
//        titleRun.setColor("0070C0"); // Цвет текста (синий в шестнадцатеричном формате)
//        titleRun.addBreak();         // Переход на новую строку после заголовка
//
//        // Добавление основного текста
//        XWPFParagraph paragraph1 = doc.createParagraph();
//        XWPFRun run1 = paragraph1.createRun();
//        run1.setText("Email: " + employee.getEmail());
//        run1.addBreak();
//        run1.setText("Phone: " + employee.getPhone());
//        run1.addBreak();
//        run1.setText("Telegram: " + employee.getTg());
//        run1.addBreak();
//        run1.setFontFamily("Times New Roman");
//        run1.setFontSize(12);
//        run1.addBreak(); // Добавляем разрыв строки
//
//        XWPFRun run2 = paragraph1.createRun();
//        run2.setText(employee.getSummary());
//
//        try {
//            String fileName = FOLDER_PATH + employee.getId() + ".docx";
//            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
//            doc.write(fileOutputStream);
//            fileOutputStream.close();
//            return fileName;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
}
