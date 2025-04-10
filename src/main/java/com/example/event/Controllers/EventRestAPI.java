package com.example.event.Controllers;

import com.example.event.Entities.Event;
import com.example.event.Services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:4200")

public class EventRestAPI {

    @Autowired
    EventService eventService;

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

    @PutMapping("/mod_event")
    public Event modEvent(@RequestBody Event event) {
        Event ev= eventService.updateEvent(event);
        return ev;
    }

    @DeleteMapping("/del_event/{idEvent}")
    public void delEvent(@PathVariable long idEvent) {
        eventService.deleteEvent(idEvent);
    }

//    @GetMapping("/show_event_by_id/{idEvent}")
//    public ResponseEntity<Event> getEventById(@PathVariable long idEvent) {
//        Event event = eventService.getEventById(idEvent);
//        return ResponseEntity.ok(event);
//    }



    @GetMapping("/top-rated-event")
    public Event getTopRatedEvent() {
        return eventService.getTopRatedEvent();
    }
}
