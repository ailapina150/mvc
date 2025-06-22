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
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/resumes")
public class ResumeController {

    Port<Employee> employeeRepository;

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
            System.out.println("*****" + employee);
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

        Employee employee = Employee.builder()
                .projects(List.of(new Project()))
                .educations(List.of(new Education()))
                .skill(new ArrayList<>())
                .build();
        System.out.println("*****" + employee);
        model.addAttribute("employee",employee);
        model.addAttribute("title","Create");
        model.addAttribute("skillsList", Skills.values());
        model.addAttribute("englishLevelsList", EnglishLevels.values());
        return "update";
    }

    @PostMapping("/update")
    public String updateContact(@ModelAttribute("employee") Employee employee,
                                @RequestParam("photoFileName") MultipartFile photoFile) {
        System.out.println("*****"+employee);

        if(employee.getEducations()!= null){
            employee.setEducations(employee.getEducations().stream().filter(Objects::nonNull).toList());
            for (Education education : employee.getEducations()) {
                education.setEmployee(employee);
            }
        }else{
            employee.setEducations(null);
        }

        if(employee.getProjects()!= null) {
            employee.setProjects(employee.getProjects().stream().filter(Objects::nonNull).toList());
            for (Project project : employee.getProjects()) {
                project.setEmployee(employee);
            }
        }else{
            employee.setProjects(null);
        }

        for(Project project: employee.getProjects()){
            if(project.getTasks()!=null) {
                project.setTasks(project.getTasks()
                        .stream()
                        .filter(e -> e != null && !e.isEmpty())
                        .toList());
            }
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
