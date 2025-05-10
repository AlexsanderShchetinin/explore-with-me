package practicum.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products", schema = "ewm_prime")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private String name;

    private String description;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private LocalDateTime updated;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime created;

    /**
     * Стоимость товара
     */
    @Column(name = "cost", precision = 20, scale = 2)
    private BigDecimal cost;

    /**
     * Владелец товара (изменяется после продажи)
     */
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /**
     * Арендатор (в случае если товар арендуют)
     */
    @ManyToOne
    @JoinColumn(name = "renter_id")
    private User renter;

    /**
     * Габариты
     */
    @OneToOne
    @JoinColumn(name = "dimension_id")
    private DimensionInt3 size;

    /**
     * коллекция рейтингов товара по отзывам пользователей
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> rating;

}
