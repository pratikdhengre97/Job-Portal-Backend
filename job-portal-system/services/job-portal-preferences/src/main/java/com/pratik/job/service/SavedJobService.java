package com.pratik.job.service;

import com.pratik.job.dto.SavedJobResponse;
import com.pratik.job.payload.SaveJobRequest;

import java.util.List;

public interface SavedJobService {

    SavedJobResponse saveJob(Long candidateId, SaveJobRequest req) throws Exception;

    void unsavedJob(Long candidateId, Long savedJobId) throws Exception;

    List<SavedJobResponse> getSavedJob(Long candidateId);

    boolean isSaved(Long candidateId, Long jobId);
}
