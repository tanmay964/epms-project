package com.example.epms.controller;

import com.example.epms.Employee;
import com.example.epms.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reviewDate,
            @RequestParam(required = false) Integer minScore,
            @RequestParam(required = false) List<Long> departmentIds,
            @RequestParam(required = false) List<Long> projectIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = employeeService.findEmployeesWithFilters(reviewDate, minScore, departmentIds, projectIds, pageable);
        var result = employees.stream().map(e -> {
            var dto = new java.util.HashMap<String, Object>();
            dto.put("id", e.getId());
            dto.put("name", e.getName());
            dto.put("email", e.getEmail());
            dto.put("departmentId", e.getDepartment() != null ? e.getDepartment().getId() : null);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok().body(java.util.Map.of(
                "content", result,
                "page", employees.getNumber(),
                "size", employees.getSize(),
                "totalElements", employees.getTotalElements()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeDetail(@PathVariable Long id) {
        var empOpt = employeeService.findById(id);
        if (empOpt.isEmpty()) return ResponseEntity.notFound().build();
        Employee e = empOpt.get();

        var detail = new java.util.HashMap<String, Object>();
        detail.put("id", e.getId());
        detail.put("name", e.getName());
        detail.put("email", e.getEmail());
        if (e.getDepartment() != null) {
            detail.put("department", java.util.Map.of("id", e.getDepartment().getId(), "name", e.getDepartment().getName()));
        }

        var projects = e.getEmployeeProjects().stream().map(ep -> java.util.Map.of(
                "id", ep.getProject().getId(),
                "name", ep.getProject().getName(),
                "role", ep.getRole(),
                "assignedDate", ep.getAssignedDate()
        )).collect(Collectors.toSet());
        detail.put("projects", projects);

        var reviews = employeeService.lastThreeReviews(e.getId()).stream().map(r -> java.util.Map.of(
                "id", r.getId(),
                "reviewDate", r.getReviewDate(),
                "score", r.getScore(),
                "comments", r.getReviewComments()
        )).collect(Collectors.toList());
        detail.put("lastThreeReviews", reviews);

        return ResponseEntity.ok(detail);
    }
}
