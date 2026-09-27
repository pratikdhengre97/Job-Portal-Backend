package com.pratik.job.dto;

import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.response.CompanyResponse;
import com.pratik.job.response.JobResponse;
import com.pratik.job.response.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;
    private UserResponse candidate;
    private Long employerId;

    private JobResponse job;
    private CompanyResponse company;

    private ApplicationStatus status;

    private Long resumeId;
    private String coverLetter;

    private BigDecimal expectedSalary;
    private LocalDate availableFrom;

    private Boolean isStarred;

    //TODO
    private List<ApplicationNoteResponse> notes;
    private LocalDateTime withdrawnAt;
    private String withdrawnReason;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

//    AI Screening Result
//    private ApplicationScreeningResponse screening;

}
