package com.pratik.job.response;

import com.pratik.job.domain.CompanySize;
import com.pratik.job.domain.CompanyStatus;
import com.pratik.job.domain.CompanyType;
import com.pratik.job.domain.IndustryType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;


    private CompanySize companySize;
    private CompanyResponse companyResponse;
    private IndustryType industryType;
    private CompanyType companyType;
    private CompanyStatus status;
    private Boolean verified;
    private Boolean active;
    private String registrationNumber;

    private Long ownerId;

    private List<SocialLinkResponse> socialLinks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime verifiedAt;
}
