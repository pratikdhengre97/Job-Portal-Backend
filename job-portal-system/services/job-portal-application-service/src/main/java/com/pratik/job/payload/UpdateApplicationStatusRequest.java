package com.pratik.job.payload;

import com.pratik.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {

    private ApplicationStatus status;
}
