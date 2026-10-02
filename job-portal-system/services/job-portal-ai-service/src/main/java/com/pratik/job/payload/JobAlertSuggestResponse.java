package com.pratik.job.payload;

import lombok.Data;

import java.util.List;

@Data
public class JobAlertSuggestResponse {

    private List<String> suggestedKeywords;
    private List<String> suggestedLocations;
    private List<String> suggestedJobType;
    private List<String> suggestedWorkModes;
    private List<String> suggestedExperienceLevels;
    private List<String> suggestedIndustries;
    private String reasoning;
}
