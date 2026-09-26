package com.pratik.job.service;

import com.pratik.job.response.JobTagResponse;
import com.pratik.job.model.JobTag;
import com.pratik.job.payload.JobTagRequest;

import java.util.List;
import java.util.Set;

public interface JobTagService {

    JobTagResponse createTag(JobTagRequest req) throws Exception;

    List<JobTagResponse> getAllTags();

    JobTagResponse getById(Long id) throws Exception;

    JobTagResponse updateTag(Long id, JobTagRequest req) throws Exception;

    void deleteTag(Long id) throws Exception;

    JobTag getTagEntityById(Long id) throws Exception;

    Set<JobTag> getTagsByIds(Set<Long> ids) throws Exception;
}
