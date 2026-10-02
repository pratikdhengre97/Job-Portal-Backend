package com.pratik.job.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchEnhanceRequest {

    @NotBlank(message = "query is required to search for jobs")
    private String query;


}
