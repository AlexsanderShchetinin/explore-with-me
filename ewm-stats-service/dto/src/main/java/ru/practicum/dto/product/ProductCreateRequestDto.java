package ru.practicum.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * DTO запроса на создание товара
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductCreateRequestDto {
    /**
     * Наименование товара
     */
    private String name;

    /**
     * Описание товара
     */
    @Length(max = 2048, message = "Описание товара не может быть больше 2048 символов")
    private String description;

    /**
     * Стоимость товара
     */
    private BigDecimal cost;

    /**
     * Владелец товара
     */
    @JsonProperty(value = "owner_id")
    private Long ownerId;

    /**
     * Габариты (длина, ширина, высота)
     */
    private Integer length;
    private Integer width;
    private Integer height;


}
