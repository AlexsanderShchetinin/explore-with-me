package ru.practicum.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO запроса на обновление пользователя
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserUpdateRequestDto {

    @JsonProperty(required = true)
    private Long id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email(message = "неверный формат email")
    private String email;

    @JsonProperty(defaultValue = "UNAUTHORIZED_USER")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String role;

    @JsonProperty(value = "admin_confirm", defaultValue = "false")
    @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
    private boolean adminConfirm;

}
