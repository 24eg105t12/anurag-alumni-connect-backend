package edu.anurag.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

public class QuestionDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateQuestionRequest {
        @NotBlank(message = "Title must not be blank")
        private String title;

        @NotBlank(message = "Body must not be blank")
        private String body;

        private String category;
        private List<String> tags;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateAnswerRequest {
        @NotBlank(message = "Answer body must not be blank")
        private String body;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResponse {
        private String id;
        private String title;
        private String body;
        private String category;
        private List<String> tags;
        private String authorId;
        private String authorName;
        private String authorRole;
        private String authorBranch;
        private Integer authorBatch;
        private Integer viewCount;
        private Integer likeCount;
        private Boolean isResolved;
        private Integer answersCount;
        private String createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerResponse {
        private String id;
        private String questionId;
        private String authorId;
        private String authorName;
        private String authorRole;
        private String authorCompany;
        private String body;
        private Integer upvotes;
        private Boolean isAccepted;
        private String createdAt;
    }
}
