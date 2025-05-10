package practicum.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс для хранения измерений.
 * По умолчанию сохраняет все габариты в миллиметрах,
 * затем в сервисном слое измерение будут переопределяться под конкретные нужды
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "dimension_integer_3", schema = "ewm_prime")
public class DimensionInt3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Длина
     */
    private int length;

    /**
     * Ширина
     */
    private int width;

    /**
     * Высота
     */
    private int height;

}
