package com.resume.restcontrollers;

import com.resume.model.Employee;
import com.resume.fabrica.FileFormat;
import com.resume.request.SendFileRequest;
import com.resume.services.FileService;
import com.resume.services.MailSenderService;
import com.resume.springdatarepositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;
    private final MailSenderService mailSender;
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getResume(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllResumes() {
        List<Employee> employees = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(employees);
        }
    }

    @PostMapping("/{id}/mail")
    public  ResponseEntity<String>  sendEmail(@PathVariable Long id) {
        Employee employee = repository.findById(id).orElse(null);
        if (employee != null) {
            mailSender.sendMail(
                    "ailapina150@gmail.com",
                    "resume of " + employee.getName(),
                    employee.getName() + " took part in " + employee.getProjects().size() + "projects"
            );
            return ResponseEntity.ok("Email about" + employee.getName() + " sent to " + employee.getEmail());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/createFile")
    public ResponseEntity<String> createDocFile(
            @PathVariable Long id,
            @RequestParam FileFormat fileFormat
    ) {
        Employee employee = repository.findById(id).orElse(null);
        if (employee != null) {
            String  fileName =  fileService.createFile(employee,fileFormat);
            return ResponseEntity.ok("File " + fileName + " has created");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/attachmentMail")
    public  ResponseEntity<String>  sendEmailWithAttachment(
            @PathVariable Long id,
            @RequestBody SendFileRequest request
    ) {
        Employee employee = repository.findById(id).orElse(null);
        if (employee != null) {
//            String  fileName = switch (request.getFileFormat()) {
//                case DOCX -> mailSender.createDOCFile(employee);
//                case EXCEL -> mailSender.createXLSFile(employee);
//            };
            mailSender.sendMailWithAttachment(
                    request.getEmailAddress(),
                    "resume of " + employee.getName(),
                    "Вы можите ознакомится с содержимым письма",
                    fileService.createFile(employee,request.getFileFormat())
            );
            return ResponseEntity.ok("Email about" + employee.getName() + " sent to " + request.getEmailAddress());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
