package Nicolo_Mecca.Progetto_W6.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String location;
    private Integer availableSeats;
    
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;


    public Event(String title, String description, LocalDateTime date, String location, Integer availableSeats) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.availableSeats = availableSeats;
    }
}
