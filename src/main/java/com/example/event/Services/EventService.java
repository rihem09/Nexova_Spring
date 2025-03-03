package com.example.event.Services;

import com.example.event.Entities.Event;
import com.example.event.Repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class EventService {
    @Autowired
    EventRepo eventRepo;

    public Event addEvent(Event event) {return eventRepo.save(event);}
    public List<Event> getAllEvents() {return eventRepo.findAll();}
    public Event updateEvent(Event event) {return eventRepo.save(event);}
    public void deleteEvent(long id) {eventRepo.deleteById(id);}
}
