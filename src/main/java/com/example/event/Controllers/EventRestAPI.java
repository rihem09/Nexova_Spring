package com.example.event.Controllers;

import com.example.event.Entities.Event;
import com.example.event.Repositories.EventRepo;
import com.example.event.Services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:4200")

public class EventRestAPI {

    @Autowired
    EventService eventService;
    @Autowired
    private EventRepo eventRepo;

    @PostMapping("/add_event")
    public Event addEvent(@RequestBody Event event) {
        Event e=eventService.addEvent(event);
        return e;
    }
    @GetMapping("/show_event")
    public List<Event> showEvent() {
        List<Event> eventList = eventService.getAllEvents();
        return eventList;
    }

    @PutMapping("/mod_event/{idEvent}")
    public Event modEvent(@RequestBody Event event) {
        Event ev= eventService.updateEvent(event);
        return ev;
    }

    @DeleteMapping("/del_event/{idEvent}")
    public void delEvent(@PathVariable long idEvent) {
        eventService.deleteEvent(idEvent);
    }


//    @GetMapping("/show_event_byid/{idEvent}")
//    public Event showEventById(@PathVariable long idEvent) {
//        return eventService.findByidEvent(idEvent);
//    }

    @GetMapping("/search_event")
    public List<Event> searchEvents(@RequestParam String title) {
        return eventService.searchByTitle(title);
    }


    @GetMapping("/top-rated-event")
    public Event getTopRatedEvent() {
        return eventService.getTopRatedEvent();
    }


    @GetMapping("/cluster/{id}")
    public List<Event> getEventsByCluster(@PathVariable("id") Integer clusterId) {
        return eventRepo.findByCluster(clusterId);
    }


    @GetMapping("/clusters")
    public Map<Integer, List<Event>> getAllClusters() {
        return eventRepo.findAll()
                .stream()
                .filter(e -> e.getCluster() != null)
                .collect(Collectors.groupingBy(Event::getCluster));
    }


}
