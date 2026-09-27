package com.pratik.job.service;

import com.pratik.job.client.CompanyClient;
import com.pratik.job.client.JobClient;
import com.pratik.job.client.ResumeClient;
import com.pratik.job.client.UserClient;
import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.dto.ApplicationResponse;
import com.pratik.job.event.ApplicationEventPublisher;
import com.pratik.job.response.CompanyResponse;
import com.pratik.job.response.JobResponse;
import com.pratik.job.response.ResumeResponse;
import com.pratik.job.response.UserResponse;
import com.pratik.job.mapper.ApplicationMapper;
import com.pratik.job.model.Application;
import com.pratik.job.model.ApplicationNote;
import com.pratik.job.payload.CompanyApplicationFilterRequest;
import com.pratik.job.payload.CreateApplicationRequest;
import com.pratik.job.payload.WithdrawApplicationRequest;
import com.pratik.job.repository.ApplicationNoteRepository;
import com.pratik.job.repository.ApplicationRepository;
import com.pratik.job.repository.ApplicationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final ResumeClient resumeClient;
    private final CompanyClient companyClient;
    private final JobClient jobClient;
    private final UserClient userClient;
    private final ApplicationNoteRepository applicationNoteRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req) throws Exception {
        if(applicationRepository.existsByCandidateIdAndJobId(candidateId, req.getJobId())) {
            throw new Exception("You have already applied");
        }

        JobResponse job = jobClient.getJobById(req.getJobId());

        Long companyId = job.getCompany().getId();
        Long employeeId = job.getEmployerId();

        ResumeResponse resume = resumeClient.getResumeById(req.getResumeId(), candidateId);

        Application application = ApplicationMapper.toEntity(
                req, candidateId, companyId, employeeId
        );

        Application savedApplication = applicationRepository.save(application);

        // Todo : Ai screening runs in a background thread, no callback needed.

        return buildFullResponse(savedApplication);
    }

    @Override
    public ApplicationResponse getApplicationById(Long id) throws Exception {
        Application application = getApplicationEntity(id);
        return buildFullResponse(application);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public ApplicationResponse updateStatus(Long applicationId,
                                            Long employerId,
                                            ApplicationStatus status) throws Exception {
        Application application = getApplicationEntity(applicationId);
        ApplicationStatus oldStatus = application.getStatus();

        assertEmployer(application, employerId);

        if(application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new Exception("Candidate have already withdrawn");
        }
        application.setStatus(status);
        Application savedApplication = applicationRepository.save(application);

        applicationEventPublisher.publishStatusChange(application,
                oldStatus, status, "your application status get changed");

        return buildFullResponse(application);
    }



    @Override
    public List<ApplicationResponse> getApplicationsForCompany(Long userId,
                                                               CompanyApplicationFilterRequest filter) {

        // fetch company by ownerId
        Long companyId = companyClient.getMyCompany(userId).getId();
        Sort sort = buildSort(filter.getSortBy());
        return applicationRepository.findAll(
                ApplicationSpecification.forCompanyWithFilters(
                companyId,
                filter.getJobId(),
                filter.getStatus(),
                filter.getIsStarred(),
                filter.getAiShortlistStatus(),
                filter.getMinAiScore()
                ), sort)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }



    @Override
    public void deleteApplication(Long applicationId, Long candidateId) throws Exception {

        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);
        applicationRepository.delete(application);

    }

    @Override
    public ApplicationResponse withdraw(Long applicationId,
                                        Long candidateId,
                                        WithdrawApplicationRequest req) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertCandidate(application, candidateId);
        application.setStatus(ApplicationStatus.WITHDRAWN);
        application.setWithdrawnReason(req.getReason());
        Application savedApplication = applicationRepository.save(application);
        return buildFullResponse(savedApplication);
    }



    @Override
    public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
        Application application = getApplicationEntity(applicationId);
        assertEmployer(application, employerId);
        application.setIsStarred(!application.getIsStarred());
        Application savedApplication = applicationRepository.save(application);
        return buildFullResponse(savedApplication);
    }

    @Override
    public Application getApplicationEntity(Long id) throws Exception {
        return applicationRepository.findById(id).orElseThrow(
                () -> new Exception("Application not found")
        );
    }

    public ApplicationResponse buildFullResponse(Application application) {

        //Todo  : fetch real data from respective microservice
    JobResponse job = jobClient.getJobById(application.getJobId());

        CompanyResponse company = companyClient.getCompanyById(application.getCompanyId());

        UserResponse candidate = userClient.getUserById(application.getCandidateId());

        List<ApplicationNote> notes = applicationNoteRepository.findByApplicationId(application.getId());

        return ApplicationMapper.toResponse(
                application,
                notes,
                job,
                company,
                candidate
        );
    }

    private Sort buildSort(String sortBy) {
        if("AI_SCORE_DESC".equals(sortBy)) {
            return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        else if("AI_SCORE_ASC".equals(sortBy)) {
            return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
        }
        return Sort.by(Sort.Direction.DESC, "appliedAt");
    }

    private void assertEmployer(Application application, Long employerId) throws Exception {
        if(!application.getEmployerId().equals(employerId)) {
            throw new Exception("You are not the employer for this application");
        }
    }
    private void assertCandidate(Application application, Long candidateId) throws Exception {
        if(!application.getCandidateId().equals(candidateId)) {
            throw new Exception("You are not the owner of this application");
        }
    }
}
