package com.ms.companyms.company.impl;

import com.ms.companyms.company.Company;
import com.ms.companyms.company.CompanyRepository;
import com.ms.companyms.company.CompanyService;
import com.ms.companyms.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setName(company.getName());
            companyRepository.save(companyToUpdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
    }

}