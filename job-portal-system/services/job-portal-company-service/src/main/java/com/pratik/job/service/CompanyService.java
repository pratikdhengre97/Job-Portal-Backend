package com.pratik.job.service;

import com.pratik.job.domain.CompanyStatus;
import com.pratik.job.domain.CompanyType;
import com.pratik.job.domain.IndustryType;
import com.pratik.job.dto.CompanyRequest;
import com.pratik.job.model.Company;
import com.pratik.job.response.CompanyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception;
    CompanyResponse getCompanyById(Long id) throws Exception;
//    CompanyResponse updateCompany(Long id, CompanyRequest req);
    CompanyResponse getMyCompany(Long ownerId) throws Exception;

    List<CompanyResponse> getAllCompanies(CompanyType companyType,
                                          IndustryType industryType,
                                          CompanyStatus companyStatus);

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req) throws Exception;
    CompanyResponse verifyCompany(Long companyId) throws Exception;
    void deleteCompany(Long companyId, Long ownerId) throws Exception;
    CompanyResponse deactivateCompany(Long companyId) throws Exception;

    Company getCompanyEntityById(Long id) throws Exception;
}
