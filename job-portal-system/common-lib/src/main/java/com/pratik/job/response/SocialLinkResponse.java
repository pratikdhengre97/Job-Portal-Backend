package com.pratik.job.response;

import com.pratik.job.domain.SocialPlatform;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinkResponse {

    private SocialPlatform platform;

    private String url;
}
