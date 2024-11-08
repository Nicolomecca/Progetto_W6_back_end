package Nicolo_Mecca.Progetto_W6.payloads;

import Nicolo_Mecca.Progetto_W6.entities.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "l'email è un campo obbligatorio!")
        @Email(message = "L'email inserita non è valida")
        String email,
        @NotEmpty(message = "La password è un campo obbligatorio")
        @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
        String password,
        @NotNull(message = "Il ruolo è un campo obbligatorio")
        UserRole role

) {
}
