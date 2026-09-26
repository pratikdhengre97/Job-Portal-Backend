package com.pratik.job.service;

import com.pratik.job.response.JobSkillResponse;
import com.pratik.job.model.JobSkill;
import com.pratik.job.payload.JobSkillRequest;

import java.util.List;
import java.util.Set;

public interface JobSkillService {

    JobSkillResponse createSkill(JobSkillRequest req) throws Exception;

    List<JobSkillResponse> getAllSkills();

    JobSkillResponse getSkillById(Long id) throws Exception;

    JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception;

    void deleteSkill(Long id) throws Exception;

    Set<JobSkill> getSkillsByIds(Set<Long> ids);

}
