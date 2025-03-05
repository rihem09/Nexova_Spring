package com.example.assurance.controller;

import com.example.assurance.entity.Application;
import com.example.assurance.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    /**
     * Exemple: créer une candidature liée à un JobOffer (ID passé dans l'URL).
     */
    @PostMapping("/joboffer/{jobOfferId}")
    public ResponseEntity<Application> createApplication(@PathVariable Long jobOfferId,
                                                         @RequestBody Application application) {
        Application created = applicationService.createApplication(application, jobOfferId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id,
                                                         @RequestBody Application updatedApp) {
        Application updated = applicationService.updateApplication(id, updatedApp);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
