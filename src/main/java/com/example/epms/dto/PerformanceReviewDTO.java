package com.example.epms.dto;

import java.time.LocalDate;

public class PerformanceReviewDTO {
    private Long id;
    private LocalDate reviewDate;
    private Integer score;
    private String reviewComments;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getReviewComments() { return reviewComments; }
    public void setReviewComments(String reviewComments) { this.reviewComments = reviewComments; }
}
