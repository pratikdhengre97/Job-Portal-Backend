package com.pratik.job.event;

import com.pratik.job.client.CompanyClient;
import com.pratik.job.client.JobClient;
import com.pratik.job.client.UserClient;
import com.pratik.job.domain.ApplicationStatus;
import com.pratik.job.model.Application;
import com.pratik.job.response.CompanyResponse;
import com.pratik.job.response.JobResponse;
import com.pratik.job.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationEventPublisher {

    public static final String TOPIC = "application.status.changed";

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;

    public void publishStatusChange(Application app,
                                    ApplicationStatus oldStatus,
                                    ApplicationStatus newStatus,
                                    String note) {
        try {

            UserResponse candidate = userClient.getUserById(app.getCandidateId());
            JobResponse job = jobClient.getJobById(app.getJobId());
            CompanyResponse company = companyClient.getCompanyById(app.getCompanyId());

            ApplicationStatusChangedEvent event = ApplicationStatusChangedEvent.builder()
                    .applicationId(app.getId())
                    .candidateId(app.getCandidateId())
                    .candidateEmail(candidate.getEmail())
                    .companyName(candidate.getFullName())
                    .oldStatus(oldStatus)
                    .newStatus(app.getStatus())
                    .note(note)
                    .jobTitle(job.getTitle())
                    .companyName(company.getName())
                    .build();

            kafkaTemplate.send(TOPIC, String.valueOf(app.getId()) ,event);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
