package Nicolo_Mecca.Progetto_W6.controllers;

import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.payloads.EventDTO;
import Nicolo_Mecca.Progetto_W6.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')") // Corretto
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody @Validated EventDTO eventDTO, @AuthenticationPrincipal User organizer) {
        return eventService.createEvent(eventDTO, organizer);
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')") // Corretto
    public Event updateEvent(@PathVariable Long eventId, @RequestBody @Validated EventDTO eventDTO, @AuthenticationPrincipal User organizer) {
        return eventService.updateEvent(eventId, eventDTO, organizer);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long eventId, @AuthenticationPrincipal User organizer) {
        eventService.deleteEvent(eventId, organizer);
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

}
