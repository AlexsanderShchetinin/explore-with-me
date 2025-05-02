package practicum.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Event {

    private UUID id;

    /**
     * Название события
     */
    private String name;

    /**
     * Краткая информация
     */
    private String briefDescription;

    /**
     * Полное описание события
     */
    private String fullDescription;

    private Category category;

    private LocalDateTime created;
    private LocalDateTime modified;

    private LocalDateTime beginning;
    private LocalDateTime ending;

    private User creator;

}
