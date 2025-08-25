package com.resume.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resume.dto.ProjectDto;
import com.resume.request.CreateProjectRequest;
import com.resume.services.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)//Настраивает mockMVC и ObjectMapper
class ProjectControllerTest {
    private static final Integer PROJECT_ID = 1;
    private static final ProjectDto PROJECT = new ProjectDto(1, "Test Project", "Test Description");
    private static final List<ProjectDto> PROJECTS = List.of(PROJECT);
    private static final String DEVELOPER_NAME = "Developer";
    private static final CreateProjectRequest REQUEST = new CreateProjectRequest("Test Project", "Test Description", DEVELOPER_NAME);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProjectService projectServices;

    @Test
    void getProjectByIdTest() throws Exception {
        when(projectServices.getProjectById(PROJECT_ID)).thenReturn(PROJECT);
        mockMvc.perform(get("/projects/" + PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PROJECT_ID))
                .andExpect(jsonPath("$.name").value(PROJECT.getName()))
                .andExpect(jsonPath("$.description").value(PROJECT.getDescription()));
        verify(projectServices, times(1)).getProjectById(PROJECT_ID);

    }

    @Test
    void getAllProjectsTest() throws Exception {
        when(projectServices.getAllProjects()).thenReturn(PROJECTS);
        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(PROJECT_ID))
                .andExpect(jsonPath("$[0].name").value(PROJECT.getName()))
                .andExpect(jsonPath("$[0].description").value(PROJECT.getDescription()));
        verify(projectServices, times(1)).getAllProjects();
    }

    @Test
    void createProjectTest() throws Exception {
        when(projectServices.createProject(REQUEST)).thenReturn(PROJECT);
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(REQUEST))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(PROJECT_ID))
                .andExpect(jsonPath("$.name").value(PROJECT.getName()))
                .andExpect(jsonPath("$.description").value(PROJECT.getDescription()));
        verify(projectServices, times(1)).createProject(REQUEST);

    }

    @Test
    void deleteProjectTest() throws Exception {

        doNothing().when(projectServices).deleteProject(PROJECT_ID);
        mockMvc.perform(delete("/projects/" + PROJECT_ID))
                .andExpect(status().isNoContent());
        verify(projectServices).deleteProject(PROJECT_ID);
    }
}
