package com.pratik.job.mapper;

import com.pratik.job.dto.SavedJobResponse;
import com.pratik.job.model.SavedJob;

public class PreferenceMapper {

    public static SavedJobResponse toSavedJobResponse(SavedJob savedJob) {

        return SavedJobResponse.builder()
                .id(savedJob.getId())
                .candidateId(savedJob.getCandidateId())
                .jobId(savedJob.getJobId())
                .savedAt(savedJob.getSavedAt())
                .build();
    }
}
