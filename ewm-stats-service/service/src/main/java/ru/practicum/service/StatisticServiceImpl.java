package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.Parameters;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.EndpointHitMapperImpl;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStatsProjection;
import ru.practicum.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;
    private final EndpointHitMapperImpl endpointHitMapper;

    @Override
    @Transactional
    public EndpointHitDto addHit(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = endpointHitMapper.toModel(endpointHitDto);
        EndpointHit saved = repository.save(endpointHit);
        return endpointHitMapper.toDto(saved);
    }

    @Override
    public List<ViewStatsDto> getStats(Parameters p) {
        final String app = "ewm-main-service";  // на данном этапе все запросы приходят из main-сервиса
        String[] uris = p.getUris();
        // если параметр uris не передавался в запросе к сервису, то возвращаем всю статистику
        if (uris[0].equals("all")) {
            List<ViewStatsProjection> viewStats;
            if (p.isUnique()) {
                viewStats = repository.findAllStatsWithUniqueIpOrderByHitsDesc(p.getStart(), p.getEnd(), app);
            } else {
                viewStats = repository.findAllStatsWithoutUniqueIpOrderByHitsDesc(p.getStart(), p.getEnd(), app);
            }
            return viewStats.stream()
                    .map(projection -> ViewStatsDto.builder()
                            .app(projection.getApp())
                            .uri(projection.getUri())
                            .hits(projection.getHits())
                            .build())
                    .collect(Collectors.toList());
        }
        // если параметр uris был передан то в цикле обращаемся к БД по каждому uri
        List<ViewStatsDto> listStats = new ArrayList<>();
        for (String uri : uris) {
            int hits;
            if (p.isUnique()) {
                hits = repository.countHitsByUriWithUniqueIp(
                        p.getStart(), p.getEnd(), uri, app);
            } else {
                hits = repository.countHitsByUriWithoutUniqueIp(
                        p.getStart(), p.getEnd(), uri, app);
            }
            listStats.add(ViewStatsDto.builder()
                    .app(app)
                    .uri(uri)
                    .hits(hits)
                    .build());
        }
        // возвращаемый arrayList сортируем по убыванию кол-ва просмотров через streamApi
        return listStats.stream()
                .sorted(Comparator.comparingInt(ViewStatsDto::getHits).reversed())
                .toList();
    }
}
