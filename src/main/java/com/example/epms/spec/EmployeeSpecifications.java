package com.example.epms.spec;

import com.example.epms.Employee;
import com.example.epms.PerformanceReview;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.time.LocalDate;
import java.util.List;

public class EmployeeSpecifications {
    public static Specification<Employee> departmentIn(List<Long> departmentIds) {
        return (root, query, cb) -> {
            if (departmentIds == null || departmentIds.isEmpty()) return cb.conjunction();
            return root.get("department").get("id").in(departmentIds);
        };
    }

    public static Specification<Employee> projectsIn(List<Long> projectIds) {
        return (root, query, cb) -> {
            if (projectIds == null || projectIds.isEmpty()) return cb.conjunction();
            query.distinct(true);
            Join<Object, Object> join = root.join("employeeProjects", JoinType.LEFT).join("project", JoinType.LEFT);
            return join.get("id").in(projectIds);
        };
    }

    public static Specification<Employee> performanceScoreOnDate(LocalDate reviewDate, Integer minScore) {
        return (root, query, cb) -> {
            if (reviewDate == null || minScore == null) return cb.conjunction();
            query.distinct(true);
            Join<Employee, PerformanceReview> join = root.join("performanceReviews", JoinType.LEFT);
            return cb.and(cb.equal(join.get("reviewDate"), reviewDate), cb.greaterThanOrEqualTo(join.get("score"), minScore));
        };
    }
}
