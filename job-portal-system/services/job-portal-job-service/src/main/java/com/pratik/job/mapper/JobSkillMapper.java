package com.pratik.job.mapper;

import com.pratik.job.response.JobSkillResponse;
import com.pratik.job.model.JobSkill;

public class JobSkillMapper {

    public static JobSkillResponse toJobSkillResponse(JobSkill skill) {
        return JobSkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .slug(skill.getSlug())
                .category(skill.getCategory())
                .active(skill.getActive())
                .build();
    }
}
