package ru.practicum.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Стандартное DTO ответа на получение информации о пользователе
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponseDto {

    @JsonProperty(required = true)
    private Long id;

    @JsonProperty(required = true)
    private String firstname;

    @JsonProperty(required = true)
    private String lastname;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String role;

    @JsonProperty(required = true)
    private LocalDateTime created;
    private LocalDateTime updated;

}
