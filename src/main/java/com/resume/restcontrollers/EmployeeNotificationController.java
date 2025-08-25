package com.resume.restcontrollers;

import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.request.SentFileRequest;
import com.resume.services.EmployeeService;
import com.resume.services.FileService;
import com.resume.services.MailSenderService;
import com.resume.services.rabbitMQ.AmqpProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification/employees")
public class EmployeeNotificationController {
    private final EmployeeService service;
    private final MailSenderService mailSender;
    private final FileService fileService;
    private final AmqpProducerService amqpProducerService;

    @GetMapping("/{id}/file")
    public ResponseEntity<String> createFile(
            @PathVariable Long id,
            @RequestParam FileFormat fileFormat
    ) {
        EmployeeDto employee = service.getById(id);
        String fileName = fileService.createFile(employee, fileFormat);
        return ResponseEntity.ok("File " + fileName + " has created");
    }

    @PostMapping("/{id}/email")
    public ResponseEntity<String> sendEmail(@PathVariable Long id) {
        EmployeeDto employee = service.getById(id);
        mailSender.sendMail(
                "ailapina150@gmail.com",
                "resume of " + employee.getName(),
                employee.getSummary()
        );
        return ResponseEntity.ok("Email about" + employee.getName() + " sent to " + employee.getEmail());
    }


    @PostMapping("/{id}/attachment-email")
    public ResponseEntity<String> sendEmailWithAttachment(
            @PathVariable Long id,
            @RequestBody @Valid SentFileRequest request
    ) {
        EmployeeDto employee = service.getById(id);
//            String  fileName = switch (request.getFileFormat()) {
//                case DOCX -> mailSender.createDOCFile(employee);
//                case EXCEL -> mailSender.createXLSFile(employee);
//            };
        mailSender.sendMailWithAttachment(
                request.getEmailAddress(),
                "resume of " + employee.getName(),
                "Вы можите ознакомится с содержимым письма",
                fileService.createFile(employee, request.getFileFormat())
        );
        return ResponseEntity.ok("Email about" + employee.getName() + " sent to " + request.getEmailAddress());
    }

    @PostMapping("/{id}/rabbit-email")
    public ResponseEntity<String> sendEmailToRabbit(
            @PathVariable Long id
    ) {
        EmployeeDto employee = service.getById(id);
        amqpProducerService.sendMessage(employee);
        return ResponseEntity.ok("Email about " + employee.getName() + " sent to Rabbit");

    }

    @PostMapping("/rabbit-emails")
    public ResponseEntity<String> sendAllToRabbit() {
        List<EmployeeDto> employees = service.getAll();
//        for(EmployeeDto employee : employees) {
//            amqpProducerService.sendMessage(employee);
//        }
        employees.forEach(amqpProducerService::sendMessage);
        return ResponseEntity.ok(employees.size() + " emails have been sent.");

    }
}
