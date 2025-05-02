package ru.practicum.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private UUID id;

    private String firstname;
    private String lastname;
    private String email;

}
