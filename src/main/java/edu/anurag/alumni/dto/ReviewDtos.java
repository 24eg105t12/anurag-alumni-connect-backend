package edu.anurag.alumni.dto;

import lombok.*;
import java.util.List;

public class ReviewDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReviewRequest {
        private String type; // RESUME, PROJECT, BOTH
        private String message;
        private String resumeUrl;
        private String projectTitle;
        private String projectUrl;
        private List<String> projectTech;
        private String projectDescription;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackSubmission {
        private Integer rating;
        private String feedback;
        private List<String> strengths;
        private List<String> improvements;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDetailResponse {
        private String id;
        private String studentId;
        private String studentName;
        private String studentBranch;
        private Integer studentBatch;
        private String reviewerId;
        private String reviewerName;
        private String reviewerCompany;
        private String type;
        private String status;
        private String message;
        private String resumeUrl;
        private String projectTitle;
        private String projectUrl;
        private List<String> projectTech;
        private String projectDescription;
        private String createdAt;
        private String claimedAt;
        private String completedAt;
        private FeedbackSummary feedback;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackSummary {
        private String id;
        private String reviewerName;
        private String reviewerCompany;
        private Integer rating;
        private String feedback;
        private List<String> strengths;
        private List<String> improvements;
        private String createdAt;
    }
}
