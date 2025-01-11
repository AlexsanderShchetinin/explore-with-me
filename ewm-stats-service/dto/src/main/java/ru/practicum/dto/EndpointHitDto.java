package ru.practicum.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.marker.Marker;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class EndpointHitDto {

    @Null(groups = Marker.Create.class, message = "при создании поле id не должно присутствовать")
    private int id;
    private String app;
    private String uri;
    private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;


}
