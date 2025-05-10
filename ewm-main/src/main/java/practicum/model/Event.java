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
@Table(name = "events", schema = "ewm_prime")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название события
     */
    private String name;

    /**
     * Краткая информация
     */
    @Column(name = "brief_description")
    private String briefDescription;

    /**
     * Полное описание события
     */
    @Column(name = "full_description")
    private String fullDescription;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime created;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime modified;

    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime beginning;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime ending;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

}
