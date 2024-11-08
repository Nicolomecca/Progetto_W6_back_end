package Nicolo_Mecca.Progetto_W6.services;

import Nicolo_Mecca.Progetto_W6.entities.Booking;
import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.excepetions.BadRequestException;
import Nicolo_Mecca.Progetto_W6.excepetions.NotFoundException;
import Nicolo_Mecca.Progetto_W6.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EventService eventService;

    public Booking createBooking(Long eventId, User user) {
        Event event = eventService.getEventById(eventId);

        if (event.getAvailableSeats() <= 0) {
            throw new BadRequestException("Non ci sono posti disponibili per questo evento");
        }

        if (bookingRepository.existsByUserAndEvent(user, event)) {
            throw new BadRequestException("Hai già prenotato questo evento");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setBookingDate(LocalDateTime.now());

        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventService.updateEvent(event.getId(), event);

        return bookingRepository.save(booking);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        Event event = booking.getEvent();

        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventService.updateEvent(event.getId(), event);

        bookingRepository.delete(booking);
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata"));
    }

    public boolean isBookingOwner(Long bookingId, User user) {
        Booking booking = getBookingById(bookingId);
        return booking.getUser().getId().equals(user.getId());
    }
}