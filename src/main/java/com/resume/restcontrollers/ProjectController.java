package com.resume.restcontrollers;

import com.resume.dto.ProjectDto;
import com.resume.request.CreateProjectRequest;
import com.resume.services.ProjectServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Проекты")
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectServices service;

    @Operation(summary = "Получить проект по идентификатору",
            description = "Достоет из базы и преобразует в ДТO данные о проекте по заданному идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(
            @Parameter(description = "идентификатор", required = true)
            @PathVariable Integer id) {
        try {
            ProjectDto project = service.getProjectById(id);
            return ResponseEntity.ok(project);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Получить данные о всех проектах",
            description = "Достоет из базы и преобразует в ДТO данные о всех проектах")
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = service.getAllProjects();
        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(projects);
        }
    }

    @Operation(summary = "Создать новый проект", description = "Создает проект на основе переданных данных.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Проект успешно создан",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProjectDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса или связанные сущности не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(
            @Parameter(description = "Данные нового проекта", required = true)
            @RequestBody @Valid CreateProjectRequest request
    ) {
        try {
            ProjectDto createdProject = service.createProject(request);
            // Возвращаем 201 Created и Location на созданный объект
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdProject.getId())
                    .toUri();
            return ResponseEntity.created(location).body(createdProject);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Operation(
            summary = "Удалить проект по идентификатору",
            description = "Удаляет проект по заданному идентификатору, если он существует"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Проект успешно удалён",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Проект с указанным идентификатором не найден",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "Идентификатор проекта", required = true)
            @PathVariable Integer id
    ) {
        try {
            service.deleteProject(id);
            // Согласно REST best practices, если успешно - 204 No Content без тела
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            // Если не найден – 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
