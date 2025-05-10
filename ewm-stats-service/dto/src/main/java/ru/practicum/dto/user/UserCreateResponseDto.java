package ru.practicum.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO ответа на создание пользователя
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCreateResponseDto {

    @JsonProperty(required = true)
    private Long id;

    @JsonProperty(required = true)
    private String firstname;

    @JsonProperty(required = true)
    private String lastname;

    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String role;

    private LocalDateTime updated;

    @JsonProperty(required = true)
    private LocalDateTime created;

}
