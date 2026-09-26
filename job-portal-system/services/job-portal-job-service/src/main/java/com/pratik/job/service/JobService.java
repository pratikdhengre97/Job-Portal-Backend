package com.pratik.job.service;

import com.pratik.job.dto.JobRequest;

import com.pratik.job.payload.JobSearchRequest;
import com.pratik.job.response.JobResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JobService {

    JobResponse createJob(Long employerId, JobRequest req) throws Exception;

    JobResponse getJobById(Long id) throws Exception;

    List<JobResponse> getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest req) throws Exception;

    JobResponse publishJob(Long jobId, Long employerId) throws Exception;

    JobResponse closeJob(Long jobId, Long employerId) throws Exception;

    void deleteJob(Long jobId, Long employerId) throws Exception;


    List<JobResponse> getAllJobsAdmin();

}
