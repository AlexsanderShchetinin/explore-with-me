package ru.practicum.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO ответа созданного товара
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductCreateResponseDto {

    @JsonProperty(value = "product_id")
    private UUID productId;

    /**
     * Наименование товара
     */
    private String name;

    /**
     * Описание товара
     */
    private String description;

    /**
     * Стоимость товара
     */
    private BigDecimal cost;

    /**
     * Владелец товара
     */
    @NotNull
    @JsonProperty(value = "owner_id")
    private Long ownerId;

    /**
     * Габариты (длина, ширина, высота)
     */
    private Integer length;
    private Integer width;
    private Integer height;

}
