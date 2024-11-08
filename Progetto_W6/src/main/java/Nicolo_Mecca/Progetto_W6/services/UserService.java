package Nicolo_Mecca.Progetto_W6.services;

import Nicolo_Mecca.Progetto_W6.entities.Booking;
import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import Nicolo_Mecca.Progetto_W6.entities.UserRole;
import Nicolo_Mecca.Progetto_W6.excepetions.BadRequestException;
import Nicolo_Mecca.Progetto_W6.excepetions.NotFoundException;
import Nicolo_Mecca.Progetto_W6.excepetions.UnauthorizedException;
import Nicolo_Mecca.Progetto_W6.payloads.UserDTO;
import Nicolo_Mecca.Progetto_W6.repositories.BookingRepository;
import Nicolo_Mecca.Progetto_W6.repositories.EventRepository;
import Nicolo_Mecca.Progetto_W6.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + "non trovato "));
    }

    public User save(UserDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("Email " + body.email() + " already in use!");
        });
        User newUser = new User();
        newUser.setEmail(body.email());
        newUser.setPassword();
        newUser.setRole(body.role());

        return userRepository.save(newUser);
    }

    public Page<User> findAll(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return userRepository.findAll(pageable);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    public User findByIdAndUpdate(Long userId, UserDTO body) {
        User found = this.findById(userId);

        if (!found.getEmail().equals(body.email())) {
            userRepository.findByEmail(body.email()).ifPresent(user -> {
                throw new BadRequestException("Email " + body.email() + " already in use!");
            });
            found.setEmail(body.email());
        }

        if (body.password() != null) {
            found.setPassword();
        }

        return userRepository.save(found);
    }

    public List<Event> getUserEvents(Long userId) {
        User user = this.findById(userId);
        if (user.getRole() != UserRole.EVENT_ORGANIZER) {
            throw new UnauthorizedException("User is not an organizer");
        }
        return eventRepository.findByOrganizer(user);
    }

    public List<Booking> getUserBookings(Long userId) {
        User user = this.findById(userId);
        return bookingRepository.findByUser(user);
    }

}


