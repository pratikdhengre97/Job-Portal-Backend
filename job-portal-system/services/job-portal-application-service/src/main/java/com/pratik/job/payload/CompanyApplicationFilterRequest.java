package com.pratik.job.payload;

import com.pratik.job.domain.AiShortlistStatus;
import com.pratik.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class CompanyApplicationFilterRequest {

    private Long jobId;

    private ApplicationStatus status;

    private Boolean isStarred=false;

    private AiShortlistStatus aiShortlistStatus;

    private Integer minAiScore;

    private String sortBy;
}
