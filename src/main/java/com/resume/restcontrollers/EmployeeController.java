package com.resume.restcontrollers;

import com.resume.annotations.SimpleLog;
import com.resume.dto.EmployeeDto;
import com.resume.request.CreateEmployeeRequest;
import com.resume.services.EmployeeService;
import com.resume.utils.Random;
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
@SimpleLog(value="dgdf")
public class EmployeeController {

    private final EmployeeService service;

//    @RandomEmployeeDto
//    private EmployeeDto randomEmployee;

//    @Lookup
//    public EmployeeDto getRandomEmployee() {
//        return null;
//    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        System.out.println("*****EmployeeController.getById****");
        EmployeeDto employeeDto = service.getById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        System.out.println("*****EmployeeController.getAll****");
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

    @PostMapping ("/random")
    public ResponseEntity<EmployeeDto> create() {
       // EmployeeDto employeeDto = service.save(randomEmployee);
       // EmployeeDto employeeDto = service.save(getRandomEmployee());
        EmployeeDto employeeDto = service.save(Random.getRandomEmployeeDto());
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
