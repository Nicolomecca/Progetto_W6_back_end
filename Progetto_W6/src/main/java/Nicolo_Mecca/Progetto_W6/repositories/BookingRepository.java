package Nicolo_Mecca.Progetto_W6.repositories;

import Nicolo_Mecca.Progetto_W6.entities.Booking;
import Nicolo_Mecca.Progetto_W6.entities.Event;
import Nicolo_Mecca.Progetto_W6.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);

    boolean existsByUserAndEvent(User user, Event event);

    List<Booking> findByEvent(Event event);
}
