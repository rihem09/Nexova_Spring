package com.example.event.Controllers;

import com.example.event.Entities.Feedback;
import com.example.event.Services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/fb")
@CrossOrigin(origins = "http://localhost:4200")
public class FeedbackRestAPI {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/add_fb")
    public Feedback addFeedback(@RequestBody Feedback feedback) {
        Feedback fb=feedbackService.addFeedback(feedback);
        return fb;
    }

    @GetMapping("/show_fb")
    public List<Feedback> showFeedback() {
        List<Feedback> fbList = feedbackService.getAllFeedback();
        return fbList;
    }

    @PutMapping("/mod_fb")
    public Feedback modFeedback(@RequestBody Feedback feedback) {
        Feedback fb= feedbackService.updateFeedback(feedback);
        return fb;
    }

    @DeleteMapping("/del_fb/{idFeedback}")
    public void delFeedback(@PathVariable("idFeedback") long idfb) {
        feedbackService.deleteFeedback(idfb);
    }
}
