package com.example.epms;

import com.example.epms.Employee;
import com.example.epms.PerformanceReview;
import com.example.epms.repository.EmployeeRepository;
import com.example.epms.repository.PerformanceReviewRepository;
import com.example.epms.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PerformanceReviewRepository reviewRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private PerformanceReview review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        review = new PerformanceReview();
        review.setId(1L);
        review.setScore(85);
    }

    @Test
    void testFindEmployeesWithFilters() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(page);

        Page<Employee> result = employeeService.findEmployeesWithFilters(
                LocalDate.now(), 80, List.of(1L), List.of(2L), PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
        verify(employeeRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testFindById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testLastThreeReviews() {
        when(reviewRepository.findTop3ByEmployeeIdOrderByReviewDateDesc(1L))
                .thenReturn(List.of(review));

        List<PerformanceReview> result = employeeService.lastThreeReviews(1L);

        assertEquals(1, result.size());
        assertEquals(85, result.get(0).getScore());
        verify(reviewRepository, times(1)).findTop3ByEmployeeIdOrderByReviewDateDesc(1L);
    }
}
