package com.example.epms;

import com.example.epms.Department;
import com.example.epms.Employee;
import com.example.epms.EmployeeProject;
import com.example.epms.Project;
import com.example.epms.PerformanceReview;
import com.example.epms.controller.EmployeeController;
import com.example.epms.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetEmployees() throws Exception {
        Department dept = new Department();
        dept.setId(10L);
        dept.setName("Engineering");

        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("Tanmay Mishra");
        emp.setEmail("tanmay@gmail.com");
        emp.setDepartment(dept);

        Page<Employee> page = new PageImpl<>(List.of(emp));

        when(employeeService.findEmployeesWithFilters(
                any(), any(), any(), any(), any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/employees")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Tanmay Mishra"))
                .andExpect(jsonPath("$.content[0].email").value("tanmay@gmail.com"))
                .andExpect(jsonPath("$.content[0].departmentId").value(10));

        verify(employeeService, times(1))
                .findEmployeesWithFilters(any(), any(), any(), any(), any(Pageable.class));
    }

    @Test
    void testGetEmployeeDetail() throws Exception {
        Department dept = new Department();
        dept.setId(10L);
        dept.setName("Engineering");

        Project project = new Project();
        project.setId(101L);
        project.setName("Project Alpha");

        EmployeeProject empProject = new EmployeeProject();
        empProject.setProject(project);
        empProject.setRole("Developer");
        empProject.setAssignedDate(LocalDate.of(2024, 5, 1));

        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("Tanmay Mishra");
        emp.setEmail("tanmay@gmail.com");
        emp.setDepartment(dept);
        emp.setEmployeeProjects(Set.of(empProject));

        PerformanceReview r1 = new PerformanceReview();
        r1.setId(1L);
        r1.setReviewDate(LocalDate.of(2024, 6, 10));
        r1.setScore(90);
        r1.setReviewComments("Above expectations");

        PerformanceReview r2 = new PerformanceReview();
        r2.setId(2L);
        r2.setReviewDate(LocalDate.of(2024, 3, 5));
        r2.setScore(85);
        r2.setReviewComments("On Track");

        when(employeeService.findById(1L)).thenReturn(Optional.of(emp));
        when(employeeService.lastThreeReviews(1L)).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Tanmay Mishra"))
                .andExpect(jsonPath("$.department.id").value(10))
                .andExpect(jsonPath("$.projects[0].name").value("Project Alpha"))
                .andExpect(jsonPath("$.lastThreeReviews[0].score").value(90))
                .andExpect(jsonPath("$.lastThreeReviews[1].score").value(85));

        verify(employeeService, times(1)).findById(1L);
        verify(employeeService, times(1)).lastThreeReviews(1L);
    }

    @Test
    void testGetEmployeeDetail_NotFound() throws Exception {
        when(employeeService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/employees/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).findById(99L);
        verify(employeeService, never()).lastThreeReviews(anyLong());
    }
}
