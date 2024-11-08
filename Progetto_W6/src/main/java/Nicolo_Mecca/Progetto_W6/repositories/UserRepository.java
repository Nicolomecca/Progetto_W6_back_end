package Nicolo_Mecca.Progetto_W6.repositories;

import Nicolo_Mecca.Progetto_W6.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
