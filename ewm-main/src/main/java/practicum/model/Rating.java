package practicum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "ratings", schema = "ewm_prime")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Покупатель который оставил рейтинг
     */
    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    /**
     * Качество товара
     */
    @Max(value = 5, message = "оценка не может быть больше 5")
    @Min(value = 0, message = "оценка не может быть меньше 0")
    private Byte quality;

    /**
     * Цена товара
     */
    @Max(value = 5, message = "оценка не может быть больше 5")
    @Min(value = 0, message = "оценка не может быть меньше 0")
    private Byte price;

    /**
     * Продаваемость
     */
    @Max(value = 5, message = "оценка не может быть больше 5")
    @Min(value = 0, message = "оценка не может быть меньше 0")
    private Byte marketability;

    /**
     * Популярность товара
     */
    @Max(value = 5, message = "оценка не может быть больше 5")
    @Min(value = 0, message = "оценка не может быть меньше 0")
    private Byte popularity;

    /**
     * Оценка владельца (актуально если товар арендуют)
     */
    @Max(value = 5, message = "оценка не может быть больше 5")
    @Min(value = 0, message = "оценка не может быть меньше 0")
    @Column(name = "owner_rating")
    private Byte ownerRating;


}
