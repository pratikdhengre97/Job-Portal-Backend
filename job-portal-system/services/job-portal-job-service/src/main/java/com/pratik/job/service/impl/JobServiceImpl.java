package com.pratik.job.service.impl;

import com.pratik.job.client.CompanyClient;
import com.pratik.job.domain.JobStatus;
import com.pratik.job.dto.JobRequest;
import com.pratik.job.mapper.JobMapper;
import com.pratik.job.model.Job;
import com.pratik.job.model.JobCategory;
import com.pratik.job.model.JobSkill;
import com.pratik.job.model.JobTag;
import com.pratik.job.model.embeddable.JobLocation;
import com.pratik.job.model.embeddable.SalaryRange;
import com.pratik.job.payload.JobSearchRequest;
import com.pratik.job.repository.JobRepository;
import com.pratik.job.repository.JobSpecification;
import com.pratik.job.response.CompanyResponse;
import com.pratik.job.response.JobResponse;
import com.pratik.job.service.JobCategoryService;
import com.pratik.job.service.JobService;
import com.pratik.job.service.JobSkillService;
import com.pratik.job.service.JobTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

    private final CompanyClient companyClient;

    @Autowired
    private final JobRepository jobRepository;

    @Autowired
    private final JobCategoryService categoryService;

    @Autowired
    private final JobSkillService skillService;

    @Autowired
    private final JobTagService tagService;

    @Override
    public JobResponse createJob(Long employerId, JobRequest req) throws Exception {

        JobCategory category = categoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills = req.getSkillIds() != null ?
                skillService.getSkillsByIds(req.getSkillIds())
        : Collections.emptySet();

        Set<JobTag> tags = req.getTagIds() != null ?
                tagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();




//        todo : fetch company by employer id

        CompanyResponse company = companyClient.getMyCompany(employerId);


        Long companyId = company.getId();

        Job job = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requirements (req.getRequirements())
                .responsibilities(req.getResponsibilities())
                .benefits(req.getBenefits())
                .companyId(companyId)
                .employerId(employerId)
                .category(category)
                .skills(skills)
                .tags(tags)
                .location(buildLocation(req))
                .salaryRange(buildSalaryRange (req))
                .jobType(req.getJobType())
                .workMode(req.getWorkMode())
                .experienceLevel(req.getExperienceLevel())
                .openings(req.getOpenings() != null ? req.getOpenings(): 1)
                .applicationDeadline(req.getApplicationDeadline())
                .expiresAt(req.getExpiresAt())
                .active(true)
                .status(JobStatus.DRAFT)
                .build();

        Job savedJob = jobRepository.save(job);
        return convertToResponse(savedJob);
    }



    @Override
    public JobResponse getJobById(Long id) throws Exception {

        Job job = jobRepository.findById(id).orElseThrow(
                () -> new Exception("Job not found")
        );
        return convertToResponse(job);
    }

    @Override
    public List<JobResponse> getJobs(JobSearchRequest request) {
        List<Job> jobs = jobRepository.findAll(JobSpecification.build(request));
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public List<JobResponse> getJobsByCompany(Long companyId) {
        List<Job> jobs = jobRepository.findByCompanyId(companyId);
        return jobs.stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found")
        );
        assertEmployer(job,employerId);

        JobCategory category = categoryService.getCategoryEntityById(req.getCategoryId());

        Set<JobSkill> skills = req.getSkillIds() != null ?
                skillService.getSkillsByIds(req.getSkillIds())
                : Collections.emptySet();

        Set<JobTag> tags = req.getTagIds() != null ?
                tagService.getTagsByIds(req.getTagIds())
                : Collections.emptySet();


        job.setTitle(req.getTitle());
        job.setDescription(req.getDescription());
        job.setRequirements(req.getRequirements());
        job.setResponsibilities(req.getResponsibilities());
        job.setBenefits(req.getBenefits());
        job.setCategory(category);
        job.setSkills(skills);
        job.setTags(tags);
        job.setLocation(buildLocation(req));
        job.setSalaryRange (buildSalaryRange(req));
        job.setJobType(req.getJobType());
        job.setWorkMode(req.getWorkMode());
        job.setExperienceLevel(req.getExperienceLevel());
        job.setOpenings(req.getOpenings() != null ? req.getOpenings(): job.getOpenings());
        job.setApplicationDeadline(req.getApplicationDeadline());
        job.setExpiresAt(req.getExpiresAt());

        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public JobResponse publishJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found")
        );
        assertEmployer(job, employerId);
        if(job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED) {
            throw new Exception("Job is expired");
        }
        job.setStatus(JobStatus.OPEN);
        job.setPublishedAt(LocalDateTime.now());
        job.setActive(true);

        return convertToResponse(jobRepository.save(job));
    }



    @Override
    public JobResponse closeJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found")
        );
        assertEmployer(job, employerId);

        if(job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.EXPIRED) {
            throw new Exception("Job is expired");
        }
        job.setStatus(JobStatus.CLOSED);
        job.setClosedAt(LocalDateTime.now());
        job.setActive(false);

        return convertToResponse(jobRepository.save(job));
    }

    @Override
    public void deleteJob(Long jobId, Long employerId) throws Exception {
        Job job = jobRepository.findById(jobId).orElseThrow(
                () -> new Exception("Job not found")
        );
        assertEmployer(job, employerId);
        jobRepository.delete(job);
    }

    @Override
    public List<JobResponse> getAllJobsAdmin() {
        return jobRepository.findAll().stream().map(
                this::convertToResponse
        ).collect(Collectors.toList());
    }

    private JobResponse convertToResponse(Job savedJob) {


        //todo : fetch company response
        CompanyResponse companyResponse = companyClient.getCompanyById(savedJob.getCompanyId());

        return JobMapper.toResponse(savedJob, companyResponse);
    }

    private SalaryRange buildSalaryRange(JobRequest req) {
        return SalaryRange.builder()
                .minSalary(req.getMinSalary())
                .maxSalary(req.getMaxSalary())
                .build();


    }

    private JobLocation buildLocation(JobRequest req) {
        return JobLocation.builder()
                .address(req.getAddress())
                .city(req.getCity())
                .state(req.getState())
                .country(req.getCountry())
                .zipCode(req.getZipCode())
                .build();
    }

    private void assertEmployer(Job job, Long employerId) throws Exception {
        if(!job.getEmployerId().equals(employerId)) {
            throw new Exception("You are not the employer who posted this job.");
        }
    }
}
