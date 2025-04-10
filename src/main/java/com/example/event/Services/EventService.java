package com.example.event.Services;

import com.example.event.Entities.Event;
import com.example.event.Repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service

public class EventService {
    @Autowired
    EventRepo eventRepo;

    public Event addEvent(Event event) {return eventRepo.save(event);}
    public List<Event> getAllEvents() {return eventRepo.findAll();}
    public Event updateEvent(Event event) {return eventRepo.save(event);}
    public void deleteEvent(long id) {eventRepo.deleteById(id);}

//    public Event getEventById(Long id) {return eventRepo.findByidEvent(id);}

    public Event getTopRatedEvent() {
        List<Event> topRatedEvents = eventRepo.findTopRatedEvent();
        return topRatedEvents.isEmpty() ? null : topRatedEvents.get(0);
    }
}
