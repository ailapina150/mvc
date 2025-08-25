package com.resume.restcontrollers;

import com.resume.dto.EmployeeDto;
import com.resume.request.CreateEmployeeRequest;
import com.resume.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> get(@PathVariable Long id) {
        EmployeeDto employeeDto = service.getById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        List<EmployeeDto> employees = service.getAll();
        if (employees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(employees);
        }
    }

   @PostMapping
   public ResponseEntity<EmployeeDto> create(@RequestBody CreateEmployeeRequest request) {
        EmployeeDto employeeDto = service.save(request.toDto());
        URI location = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/{id}")
               .buildAndExpand(employeeDto.getId())
               .toUri();
       return ResponseEntity.created(location).body(employeeDto);
   }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            // Согласно REST best practices, если успешно - 204 No Content без тела
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            // Если не найден – 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

}
