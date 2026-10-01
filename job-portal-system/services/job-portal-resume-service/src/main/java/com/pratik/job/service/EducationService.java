package com.pratik.job.service;

import com.pratik.job.response.EducationResponse;
import com.pratik.job.payload.AddEducationRequest;

import java.util.List;

public interface EducationService {

    EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest request) throws Exception;

    List<EducationResponse> getEducations(Long resumeId);

    EducationResponse updateEducation(Long educationId,
                                      Long resumeId,
                                      Long candidateId,
                                      AddEducationRequest req) throws Exception;
    void deleteEducation(Long educationId, Long resumeId, Long candidateId) throws Exception;


}
