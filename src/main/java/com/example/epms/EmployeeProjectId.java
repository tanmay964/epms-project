package com.example.epms;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeProjectId implements Serializable {
    private Long employeeId;
    private Long projectId;

    public EmployeeProjectId() {}
    public EmployeeProjectId(Long employeeId, Long projectId) { this.employeeId = employeeId; this.projectId = projectId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeProjectId)) return false;
        EmployeeProjectId that = (EmployeeProjectId) o;
        return Objects.equals(employeeId, that.employeeId) && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() { return Objects.hash(employeeId, projectId); }
}
