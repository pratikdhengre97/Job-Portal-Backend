package com.pratik.job.mapper;


import com.pratik.job.response.SocialLinkResponse;
import com.pratik.job.model.Company;
import com.pratik.job.model.SocialLink;
import com.pratik.job.response.CompanyResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {

    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLinks) {
        return SocialLinkResponse.builder()
                .platform(socialLinks.getPlatform())
                .url(socialLinks.getUrl())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {

        List<SocialLinkResponse> socialLinks = company.getSocialLinks() == null ? Collections.emptyList()
                :company.getSocialLinks().stream()
                .map(CompanyMapper::toSocialLinkResponse)
                .collect(Collectors.toList());

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .status(company.getStatus())
                .active(company.getActive())
                .industryType(company.getIndustryType())
                .registrationNumber(company.getRegistrationNumber())
                .ownerId(company.getOwnerId())
                .socialLinks(socialLinks)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
