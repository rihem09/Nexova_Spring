package com.example.event.Services;

import com.example.event.Entities.Event;
import com.example.event.Entities.Feedback;
import com.example.event.Repositories.EventRepo;
import com.example.event.Repositories.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
@Service
public class FeedbackService {
    @Autowired
    FeedbackRepo feedbackRepo;
    @Autowired
    EventRepo eventRepo;

    public Feedback addFeedback(Feedback feedback) {
        Event event = eventRepo.findById(feedback.getEvent().getIdEvent())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        feedback.setEvent(event);
        return feedbackRepo.save(feedback);
    }
    public List<Feedback> getAllFeedback() {return feedbackRepo.findAll();}
    public Feedback updateFeedback(Feedback feedback) {return feedbackRepo.save(feedback);}
    public void deleteFeedback(long id) {feedbackRepo.deleteById(id);}
    public List<Feedback> getFeedbacksByEvent(Long eventId) {
        return feedbackRepo.findByEventIdEvent(eventId);
    }


}
