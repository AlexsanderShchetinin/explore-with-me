package ewm_stats_service.service;

import ewm_stats_dto.dto.EndpointHitDto;
import ewm_stats_dto.dto.Parameters;
import ewm_stats_dto.dto.ViewStatsDto;

import java.util.List;

public interface StatisticService {

    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(Parameters parameters);

}
