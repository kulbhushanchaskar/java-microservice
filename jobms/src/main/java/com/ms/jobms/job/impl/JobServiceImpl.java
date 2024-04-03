package com.ms.jobms.job.impl;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.JobRepository;
import com.ms.jobms.job.JobService;
import com.ms.jobms.job.clients.CompanyClient;
import com.ms.jobms.job.dto.JobWithCompanyDTO;
import com.ms.jobms.job.external.Company;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    // private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CompanyClient companyClient;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    //@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    //@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = new ArrayList<>();

        return jobs.stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e) {
        return Arrays.asList("may be dummy data from another db call");
    }

    private JobWithCompanyDTO convertToDto(Job job) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        /*Company company = restTemplate.getForObject(
                "http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(),
                Company.class);*/
        Company company = companyClient.getCompany(job.getCompanyId());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}