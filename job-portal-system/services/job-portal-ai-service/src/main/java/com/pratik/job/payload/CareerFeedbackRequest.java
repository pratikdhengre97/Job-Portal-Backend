package com.pratik.job.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CareerFeedbackRequest {

    @NotBlank(message = "Resume content is required")
    private String resumeContent;

    private String targetJobTitle;
}
