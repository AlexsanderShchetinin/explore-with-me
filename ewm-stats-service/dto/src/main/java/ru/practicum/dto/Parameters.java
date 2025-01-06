package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = {"start", "end", "uris", "unique"})
public class Parameters {

    LocalDateTime start;
    LocalDateTime end;
    String[] uris;
    boolean unique;

}
