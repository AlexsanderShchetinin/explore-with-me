package practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "users", schema = "ewm_prime")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime updated;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;


}
