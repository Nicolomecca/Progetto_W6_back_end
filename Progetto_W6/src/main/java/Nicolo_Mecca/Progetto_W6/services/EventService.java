package Nicolo_Mecca.Progetto_W6.services;

import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.excepetions.BadRequestException;
import Nicolo_Mecca.Progetto_W6.excepetions.NotFoundException;
import Nicolo_Mecca.Progetto_W6.payloads.EventDTO;
import Nicolo_Mecca.Progetto_W6.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(EventDTO eventDTO, User organizer) {
        if (eventDTO.availableSeats() <= 0) {
            throw new BadRequestException("Il numero di posti deve essere maggiore di 0");
        }
        Event event = new Event();
        event.setTitle(eventDTO.title());
        event.setDescription(eventDTO.description());
        event.setDate(eventDTO.date());
        event.setLocation(eventDTO.location());
        event.setAvailableSeats(eventDTO.availableSeats());
        event.setOrganizer(organizer);

        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventDTO eventDTO, User organizer) {
        Event event = getEventById(eventId);
        event.setTitle(eventDTO.title());
        event.setDescription(eventDTO.description());
        event.setDate(eventDTO.date());
        event.setLocation(eventDTO.location());
        event.setAvailableSeats(eventDTO.availableSeats());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, Event event) {
        Event existingEvent = getEventById(eventId);
        existingEvent.setAvailableSeats(event.getAvailableSeats());
        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long eventId, User organizer) {
        Event event = getEventById(eventId);
        eventRepository.delete(event);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Evento non trovato"));
    }
}