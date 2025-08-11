package com.example.epms.dto;

public class DepartmentDTO {
    private Long id;
    private String name;
    private Double budget;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
}
