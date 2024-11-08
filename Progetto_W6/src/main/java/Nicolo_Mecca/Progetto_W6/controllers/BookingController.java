package Nicolo_Mecca.Progetto_W6.controllers;

import Nicolo_Mecca.Progetto_W6.entities.Booking;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.excepetions.UnauthorizedException;
import Nicolo_Mecca.Progetto_W6.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/events/{eventId}")
    @PreAuthorize("hasAuthority('NORMAL_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(
            @PathVariable Long eventId,
            @AuthenticationPrincipal User user
    ) {
        return bookingService.createBooking(eventId, user);
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasAnyAuthority('NORMAL_USER', 'EVENT_ORGANIZER')")
    public List<Booking> getUserBookings(@AuthenticationPrincipal User user) {
        return bookingService.getUserBookings(user);
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('NORMAL_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal User user
    ) {
        if (!bookingService.isBookingOwner(bookingId, user)) {
            throw new UnauthorizedException("Non sei autorizzato a cancellare questa prenotazione");
        }
        bookingService.cancelBooking(bookingId);
    }
}