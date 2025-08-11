package com.example.epms.dto;

import java.time.LocalDate;
import java.util.List;

public class EmployeeDetailDTO {
    private Long id;
    private String name;
    private String email;
    private DepartmentDTO department;
    private LocalDate dateOfJoining;
    private Double salary;
    private EmployeeDTO manager;
    private List<ProjectDTO> projects;
    private List<PerformanceReviewDTO> lastThreeReviews;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public DepartmentDTO getDepartment() { return department; }
    public void setDepartment(DepartmentDTO department) { this.department = department; }
    public LocalDate getDateOfJoining() { return dateOfJoining; }
    public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
    public EmployeeDTO getManager() { return manager; }
    public void setManager(EmployeeDTO manager) { this.manager = manager; }
    public List<ProjectDTO> getProjects() { return projects; }
    public void setProjects(List<ProjectDTO> projects) { this.projects = projects; }
    public List<PerformanceReviewDTO> getLastThreeReviews() { return lastThreeReviews; }
    public void setLastThreeReviews(List<PerformanceReviewDTO> lastThreeReviews) { this.lastThreeReviews = lastThreeReviews; }
}
