package Nicolo_Mecca.Progetto_W6.payloads;

import Nicolo_Mecca.Progetto_W6.entities.UserRole;

public record UserResponseDTO(
        Long id,
        String name,
        String surname,
        String email,
        UserRole role
) {
}
