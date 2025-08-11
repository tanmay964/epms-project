package com.example.epms.service;

import com.example.epms.PerformanceReview;
import com.example.epms.Employee;
import com.example.epms.repository.EmployeeRepository;
import com.example.epms.repository.PerformanceReviewRepository;
import com.example.epms.spec.EmployeeSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PerformanceReviewRepository reviewRepository;

    public EmployeeService(EmployeeRepository employeeRepository, PerformanceReviewRepository reviewRepository) {
        this.employeeRepository = employeeRepository;
        this.reviewRepository = reviewRepository;
    }

    public Page<Employee> findEmployeesWithFilters(LocalDate reviewDate, Integer minScore, List<Long> departmentIds, List<Long> projectIds, Pageable pageable) {
        Specification<Employee> spec = Specification.where(EmployeeSpecifications.departmentIn(departmentIds))
                .and(EmployeeSpecifications.projectsIn(projectIds))
                .and(EmployeeSpecifications.performanceScoreOnDate(reviewDate, minScore));
        return employeeRepository.findAll(spec, pageable);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<PerformanceReview> lastThreeReviews(Long employeeId) {
        return reviewRepository.findTop3ByEmployeeIdOrderByReviewDateDesc(employeeId);
    }
}
