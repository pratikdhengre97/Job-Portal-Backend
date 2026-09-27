package com.pratik.job.service;

import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.dto.ApplicationResponse;
import com.pratik.job.model.Application;
import com.pratik.job.payload.CompanyApplicationFilterRequest;
import com.pratik.job.payload.CreateApplicationRequest;
import com.pratik.job.payload.WithdrawApplicationRequest;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse createApplication(
            Long candidateId,
            CreateApplicationRequest req) throws Exception;

    ApplicationResponse getApplicationById(Long id) throws Exception;

    List<ApplicationResponse> getMyApplications(Long candidateId);

    List<ApplicationResponse> getApplicationsForJob(Long jobId);

    ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status) throws Exception;

    List<ApplicationResponse> getApplicationsForCompany(Long userId,
                                                        CompanyApplicationFilterRequest request);

    void deleteApplication(Long applicationId, Long candidateId) throws Exception;

    ApplicationResponse withdraw(Long applicationId, Long candidateId, WithdrawApplicationRequest req) throws Exception;

    ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception;

    Application getApplicationEntity(Long id) throws Exception;



}
