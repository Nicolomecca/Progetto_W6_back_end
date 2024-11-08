package Nicolo_Mecca.Progetto_W6.controllers;

import Nicolo_Mecca.Progetto_W6.entities.Booking;
import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.excepetions.BadRequestException;
import Nicolo_Mecca.Progetto_W6.payloads.UserDTO;
import Nicolo_Mecca.Progetto_W6.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return userService.findAll(page, size, sortBy);
    }

    // GET /users/me - Profilo utente corrente
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    // PUT /users/me - Modifica profilo
    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentUser,
                              @RequestBody @Validated UserDTO body,
                              BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Errori nel payload: " + message);
        }
        return userService.findByIdAndUpdate(currentUser.getId(), body);
    }

    // GET /users/me/events - Eventi creati dall'organizzatore
    @GetMapping("/me/events")
    @PreAuthorize("hasAuthority('EVENT_ORGANIZER')")
    public List<Event> getMyEvents(@AuthenticationPrincipal User currentUser) {
        return userService.getUserEvents(currentUser.getId());
    }

    // GET /users/me/bookings - Prenotazioni dell'utente
    @GetMapping("/me/bookings")
    public List<Booking> getMyBookings(@AuthenticationPrincipal User currentUser) {
        return userService.getUserBookings(currentUser.getId());
    }
}

