package com.resume.restcontrollers;

import com.resume.dto.EmployeeDto;
import com.resume.model.FileFormat;
import com.resume.request.SendFileRequest;
import com.resume.services.rabbitMQ.AmqpProducerService;
import com.resume.services.EmployeeService;
import com.resume.services.FileService;
import com.resume.services.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final MailSenderService mailSender;
    private final FileService fileService;
    private final AmqpProducerService amqpProducerService;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getResume(@PathVariable Long id) {
        EmployeeDto employeeDto = service.getEmployeeById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllResumes() {
        List<EmployeeDto> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(employees);
        }
    }

    @PostMapping("/{id}/sentEmail")
    public ResponseEntity<String> sendEmail(@PathVariable Long id) {
        EmployeeDto employee = service.getEmployeeById(id);
        mailSender.sendMail(
                "ailapina150@gmail.com",
                "resume of " + employee.getName(),
                employee.getSummary()
        );
        return ResponseEntity.ok("Email about" + employee.getName() + " sent to " + employee.getEmail());
    }

    @GetMapping("/{id}/createFile")
    public ResponseEntity<String> createDocFile(
            @PathVariable Long id,
            @RequestParam FileFormat fileFormat
    ) {
        EmployeeDto employee = service.getEmployeeById(id);
        String fileName = fileService.createFile(employee, fileFormat);
        return ResponseEntity.ok("File " + fileName + " has created");
    }

    @PostMapping("/{id}/sentAttachmentMail")
    public ResponseEntity<String> sendEmailWithAttachment(
            @PathVariable Long id,
            @RequestBody SendFileRequest request
    ) {
        EmployeeDto employee = service.getEmployeeById(id);
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

    @PostMapping("/{id}/sentToRabbit")
    public ResponseEntity<String> sendEmailToRabbit(
            @PathVariable Long id
    ) {
        EmployeeDto employee = service.getEmployeeById(id);
            amqpProducerService.sendMessage(employee);
            return ResponseEntity.ok("Email about " + employee.getName() + " sent to Rabbit");

    }
}
