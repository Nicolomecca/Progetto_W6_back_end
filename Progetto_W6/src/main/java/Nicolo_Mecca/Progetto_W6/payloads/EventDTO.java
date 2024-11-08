package Nicolo_Mecca.Progetto_W6.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EventDTO(
        @NotEmpty String title,
        @NotEmpty String description,
        @NotNull LocalDateTime date,
        @NotEmpty String location,
        @Min(1) int availableSeats
) {
}
