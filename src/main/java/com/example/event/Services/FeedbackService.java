package com.example.event.Services;

import com.example.event.Entities.Feedback;
import com.example.event.Repositories.FeedbackRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackService {
    @Autowired
    FeedbackRepo feedbackRepo;

    public Feedback addFeedback(Feedback feedback) {return feedbackRepo.save(feedback);}
    public List<Feedback> getAllFeedback() {return feedbackRepo.findAll();}
    public Feedback updateFeedback(Feedback feedback) {return feedbackRepo.save(feedback);}
    public void deleteFeedback(long id) {feedbackRepo.deleteById(id);}
}
