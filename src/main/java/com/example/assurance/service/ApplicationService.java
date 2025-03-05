package com.example.assurance.service;

import com.example.assurance.entity.Application;
import com.example.assurance.entity.JobOffer;
import com.example.assurance.repository.ApplicationRepository;
import com.example.assurance.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id).get();
    }

    public Application createApplication(Application application, Long jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).get();
        application.setJobOffer(jobOffer);
        return applicationRepository.save(application);
    }

    public Application updateApplication(Long id, Application updatedApp) {
        Application existing = getApplicationById(id);
        existing.setCoverLetter(updatedApp.getCoverLetter());
        existing.setResume(updatedApp.getResume());
        existing.setApplicationDate(updatedApp.getApplicationDate());
        existing.setInterviewSchedule(updatedApp.getInterviewSchedule());
        // On ne modifie pas forcément la relation jobOffer, à vous de voir
        return applicationRepository.save(existing);
    }

    public void deleteApplication(Long id) {
        // Vérification d'existence
        applicationRepository.findById(id).get();
        applicationRepository.deleteById(id);
    }
}
