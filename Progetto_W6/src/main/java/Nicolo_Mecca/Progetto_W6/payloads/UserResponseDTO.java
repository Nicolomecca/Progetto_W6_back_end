package Nicolo_Mecca.Progetto_W6.payloads;

import Nicolo_Mecca.Progetto_W6.entities.UserRole;

public record UserResponseDTO(
        Long id,
        String email,
        UserRole role
) {
}
