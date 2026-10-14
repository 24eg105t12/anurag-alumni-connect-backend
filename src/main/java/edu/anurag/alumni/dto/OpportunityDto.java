package edu.anurag.alumni.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDto {
    private String id;
    
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company is required")
    private String company;

    private String type;
    private String location;
    private Boolean isRemote;
    private String description;
    private String requirements;
    private List<String> skillsRequired;
    private Integer salaryMin;
    private Integer salaryMax;
    private List<String> eligibleBranches;
    private List<Integer> eligibleBatches;
    private Double minCgpa;
    private LocalDate deadline;

    @NotBlank(message = "Apply link is required")
    private String applyLink;

    private Boolean isReferralAvailable;
    private Boolean isActive;
    
    // Poster details
    private String postedById;
    private String postedByName;
    private String postedByRole;
    private String postedByCompany;
    private Integer postedByBatch;
    private String createdAt;
}
