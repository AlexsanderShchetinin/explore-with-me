package ru.practicum.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO запроса на создание пользователя
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCreateRequestDto {

    @JsonProperty(required = true)
    @NotBlank
    private String firstname;

    @JsonProperty(required = true)
    @NotBlank
    private String lastname;

    @Email(message = "неверный формат email")
    private String email;

    @JsonProperty(defaultValue = "UNAUTHORIZED_USER", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String role;

}
