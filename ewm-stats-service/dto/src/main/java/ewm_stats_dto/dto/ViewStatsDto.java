package ewm_stats_dto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ViewStatsDto {

    String app;
    String uri;
    int hits;

}
