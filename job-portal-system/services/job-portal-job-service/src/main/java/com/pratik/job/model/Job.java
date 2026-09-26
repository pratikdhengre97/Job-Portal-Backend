package com.pratik.job.model;

import com.pratik.job.domain.ExperienceLevel;
import com.pratik.job.domain.JobStatus;
import com.pratik.job.domain.JobType;
import com.pratik.job.domain.WorkMode;
import com.pratik.job.model.embeddable.SalaryRange;
import com.pratik.job.model.embeddable.JobLocation;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String requirements;

    private String responsibilities;

    private String benefits;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long employerId;

    @ManyToOne
    private JobCategory category;

    @ManyToMany
    private Set<JobSkill> skills;

    @ManyToMany
    private Set<JobTag> tags;

    @Embedded
    private JobLocation location;

    @Embedded
    private SalaryRange salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType jobType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceLevel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status = JobStatus.DRAFT;

    private Integer openings = 1;

    private LocalDate applicationDeadline;

    private LocalDate expiresAt;

    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;
    private LocalDateTime closedAt;

}
