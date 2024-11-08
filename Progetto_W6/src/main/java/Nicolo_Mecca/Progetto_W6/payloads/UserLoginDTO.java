package Nicolo_Mecca.Progetto_W6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(
        @NotEmpty(message = "L'email è un campo obbligatorio!")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è un campo obbligatorio")
        String password
) {
}