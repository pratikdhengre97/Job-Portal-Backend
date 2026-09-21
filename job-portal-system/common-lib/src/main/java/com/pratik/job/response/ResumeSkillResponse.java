package com.pratik.job.response;

import com.pratik.job.domain.ProficiencyLevel;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeSkillResponse {

    private Long id;
    private String skillName;
    private ProficiencyLevel proficiencyLevel;
    private Integer yearsOfExperience;
    private LocalDateTime createdAt;
    private Integer displayOrder;
}
