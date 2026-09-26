package com.pratik.job.mapper;

import com.pratik.job.response.JobTagResponse;
import com.pratik.job.model.JobTag;

public class JobTagMapper {

    public static JobTagResponse toTagResponse(JobTag jobTag) {
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }
}
