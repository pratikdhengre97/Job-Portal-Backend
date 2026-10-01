package com.pratik.job.payload;

import com.pratik.job.domain.ResumeTemplate;
import com.pratik.job.domain.ResumeVisibility;
import com.pratik.job.model.Resume;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateResumeRequest {

    @NotBlank(message = "Resume title is required")
    private String title;


    private ResumeTemplate template;

    private ResumeVisibility visibility;

    private Boolean isDefault;
}
