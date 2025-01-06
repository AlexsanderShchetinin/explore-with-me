package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.Parameters;
import ru.practicum.dto.ViewStatsDto;

import java.util.List;

public interface StatisticService {

    EndpointHitDto addHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getStats(Parameters parameters);

}
