package ru.practicum.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO ответа на получение базовой (ограниченной) информации о пользователе
 * используется для ответа неавторизованным пользователям
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserShortResponseDto {

    @JsonProperty(required = true)
    private Long id;

    @JsonProperty(required = true)
    private String firstname;

    @JsonProperty(required = true)
    private String lastname;

    @JsonProperty(required = true)
    private LocalDateTime created;


}
