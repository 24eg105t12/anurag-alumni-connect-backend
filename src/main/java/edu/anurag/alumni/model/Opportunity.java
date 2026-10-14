package edu.anurag.alumni.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String company;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    private String location;

    @Column(name = "is_remote")
    @Builder.Default
    private Boolean isRemote = false;

    @Column(columnDefinition = "MEDIUMTEXT", nullable = false)
    private String description;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "skills_required", columnDefinition = "JSON")
    private String skillsRequired;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "eligible_branches", columnDefinition = "JSON")
    private String eligibleBranches;

    @Column(name = "eligible_batches", columnDefinition = "JSON")
    private String eligibleBatches;

    @Column(name = "min_cgpa")
    private Double minCgpa;

    private LocalDate deadline;

    @Column(name = "apply_link", nullable = false)
    private String applyLink;

    @Column(name = "is_referral_available")
    @Builder.Default
    private Boolean isReferralAvailable = false;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum JobType {
        INTERNSHIP,
        FULL_TIME,
        CONTRACT,
        FREELANCE
    }
}
