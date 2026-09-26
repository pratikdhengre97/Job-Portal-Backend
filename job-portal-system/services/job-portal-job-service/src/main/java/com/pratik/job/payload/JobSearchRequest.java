package com.pratik.job.payload;

import com.pratik.job.domain.ExperienceLevel;
import com.pratik.job.domain.JobStatus;
import com.pratik.job.domain.JobType;
import com.pratik.job.domain.WorkMode;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  JobSearchRequest {

    private String keyword;

    private Long categoryId;

    private List<Long> skillIds;

    private List<Long> tagIds;

    private Long companyId;

    private String location;

    private BigDecimal minSalary;

    private BigDecimal maxSalary;

    private JobType jobType;

    private WorkMode workMode;

    private ExperienceLevel experienceLevel;

    private JobStatus status;

    private Integer minOpenings;
    private Integer maxOpenings;
}
