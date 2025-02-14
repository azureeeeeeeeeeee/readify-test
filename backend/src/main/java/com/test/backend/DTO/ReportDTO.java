package com.test.backend.DTO;

import com.test.backend.models.Report;

import java.time.LocalDateTime;

public class ReportDTO {
    public Integer id;
    public String createdBy;
    public Integer reviewId;
    public String content;
    public Boolean isResolved;
    public LocalDateTime createdAt;

    public ReportDTO(Report report) {
        this.id = report.getId();
        this.createdBy = report.getUser().getUsername();
        this.reviewId = report.getReview().getId();
        this.content = report.getContent();
        this.isResolved = report.getResolved();
        this.createdAt = report.getCreatedAt();
    }
}
