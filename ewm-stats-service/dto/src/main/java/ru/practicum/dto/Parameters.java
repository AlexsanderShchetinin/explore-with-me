package ru.practicum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = {"start", "end", "uris", "unique"})
public class Parameters {

private LocalDateTime start;
private LocalDateTime end;
private String[] uris;
private boolean unique;

}
