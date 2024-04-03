package com.ms.companyms.company;

import com.ms.companyms.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    boolean updateCompany(Company company, Long id);
    void createCompany(Company company);
    boolean deleteCompanyById(Long id);
    Company getCompanyById(Long id);
    public void updateCompanyRating(ReviewMessage reviewMessage);
}
