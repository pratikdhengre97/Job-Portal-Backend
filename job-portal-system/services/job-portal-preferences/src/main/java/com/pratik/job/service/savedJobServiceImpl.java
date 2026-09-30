package com.pratik.job.service;


import com.pratik.job.dto.SavedJobResponse;
import com.pratik.job.mapper.PreferenceMapper;
import com.pratik.job.model.SavedJob;
import com.pratik.job.payload.SaveJobRequest;
import com.pratik.job.repository.SavedJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class savedJobServiceImpl implements SavedJobService{


    private final SavedJobRepository savedJobRepository;

    @Override
    public SavedJobResponse saveJob(Long candidateId, SaveJobRequest req) throws Exception {
        if(isSaved(candidateId, req.getJobId())) {
            throw new Exception("Job already saved");
        }
        SavedJob savedJob = SavedJob.builder()
                .candidateId(candidateId)
                .jobId(req.getJobId())
                .build();

        savedJob = savedJobRepository.save(savedJob);
        return PreferenceMapper.toSavedJobResponse(savedJob);
    }

    @Override
    public void unsavedJob(Long candidateId, Long savedJobId) throws Exception {
        SavedJob savedJob = savedJobRepository.findById(savedJobId).orElseThrow(
                () -> new Exception("Job not found")
        );
        if(!savedJob.getCandidateId().equals(candidateId)) {
            throw new Exception("Job not saved");
        }
        savedJobRepository.delete(savedJob);
    }

    @Override
    public List<SavedJobResponse> getSavedJob(Long candidateId) {
        return savedJobRepository.findByCandidateId(candidateId)
                .stream().map(PreferenceMapper::toSavedJobResponse).toList();
    }

    @Override
    public boolean isSaved(Long candidateId, Long jobId) {
        return savedJobRepository.existsByCandidateIdAndJobId(candidateId, jobId);

    }
}
