package com.example.assurance.service;


import com.example.assurance.entity.JobOffer;
import com.example.assurance.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    public JobOffer getJobOfferById(Long id) {
        return jobOfferRepository.findById(id).get();
    }

    public JobOffer createJobOffer(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    public JobOffer updateJobOffer(Long id, JobOffer updatedOffer) {
        JobOffer existing = getJobOfferById(id);
        existing.setJobTitle(updatedOffer.getJobTitle());
        existing.setCompanyName(updatedOffer.getCompanyName());
        existing.setJobDescription(updatedOffer.getJobDescription());
        existing.setLocation(updatedOffer.getLocation());
        existing.setJobType(updatedOffer.getJobType());
        existing.setSalary(updatedOffer.getSalary());
        existing.setApplicationDeadline(updatedOffer.getApplicationDeadline());
        return jobOfferRepository.save(existing);
    }

    public void deleteJobOffer(Long id) {
        // Vérification d'existence
        jobOfferRepository.findById(id).get();
        jobOfferRepository.deleteById(id);
    }
}
