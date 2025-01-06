package ewm_stats_dto.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import ewm_stats_dto.marker.Marker;
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
    int id;
    String app;
    String uri;
    String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    String timestamp;


}
