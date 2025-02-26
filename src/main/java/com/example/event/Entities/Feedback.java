package com.example.event.Entities;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idFeed;
    int rating;
    String comment;

    @ManyToOne
    Event event;

    public long getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(long idFeed) {
        this.idFeed = idFeed;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}