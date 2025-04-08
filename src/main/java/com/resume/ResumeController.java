package com.resume;

import com.resume.model.*;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

    EmployeeRepository<Employee> employeeRepository;

    @GetMapping("/{id}")
    public String getResume(Model model, @PathVariable Long id) {
        if(employeeRepository.findById(id).isPresent()) {
            Employee employee = employeeRepository.findById(id).get();
            model.addAttribute("employee", employee);
            return "resume";
        }
        return "error";
    }

    @GetMapping
    public String getAllResumes(Model model) {
            List<Employee> employees = employeeRepository.findAll().stream()
                    .sorted(Comparator.comparing(Employee::getName)).toList();
            model.addAttribute("employees", employees);
            return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteResume(@PathVariable Long id){
        employeeRepository.deleteById(id);
        return "redirect:/resumes";
    }

    @GetMapping("/edit/{id}")
    public String editResume(Model model,@PathVariable Long id){
        if(employeeRepository.findById(id).isPresent()) {
            Employee employee = employeeRepository.findById(id).get();
            model.addAttribute("employee", employee);
            model.addAttribute("title", "Update");
            model.addAttribute("skillsList", Skills.values());
            model.addAttribute("englishLevelsList", EnglishLevels.values());
            return "update";
        }
        return "redirect:/resumes";
    }

    @GetMapping("/create")
    public String createResume(Model model){
        Long id = employeeRepository.findAll()
                .stream()
                .map(Employee::getId)
                .min(Long::compareTo)
                .orElse(0L);

        Employee employee = Employee.builder()
                .id(id + 1)
                .projects(List.of( Project.builder().tasks(List.of("","","")).build()))
                .educations(List.of(new Education()))
                .build();
        if(employee == null){
            return "redirect:/resumes";
        }
        model.addAttribute("employee",employee);
        model.addAttribute("title","Create");
        model.addAttribute("skillsList", Skills.titles());
        model.addAttribute("englishLevelsList", EnglishLevels.values());
        return "update";
    }

    @PostMapping("/update")
    public String updateContact(@ModelAttribute("employee") Employee employee,
                                @RequestParam("photoFileName") MultipartFile photoFile) {
        System.out.println("*****"+employee);
        employee.setEducations(employee.getEducations().stream().filter(Objects::nonNull).toList());
        employee.setProjects(employee.getProjects().stream().filter(Objects::nonNull).toList());
        for(Project project: employee.getProjects()){
            project.setTasks(project.getTasks().stream().filter(e->e!=null && !e.isEmpty()).toList());
        }



        if(!photoFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(photoFile.getOriginalFilename());
            String filePath = "src/images/" + fileName; // Замените на ваш путь
            Path path = Paths.get(filePath);
            try{
                Files.copy(photoFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                byte[] fileBytes = Files.readAllBytes(path);
                employee.setPhoto("data:image/png;base64," + Base64.getEncoder().encodeToString(fileBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        employeeRepository.save(employee);
        return "redirect:/resumes";
    }

}
