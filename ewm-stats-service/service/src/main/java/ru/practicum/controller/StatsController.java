package ru.practicum.controller;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.Parameters;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatisticService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StatsController {

    private final StatisticService service;


    @PostMapping(path = "/hit")
    public ResponseEntity<EndpointHitDto> addHit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.info("StatsController starting method addHit: adding hit={}", endpointHitDto);
        EndpointHitDto respServiceEndpointHitDto = service.addHit(endpointHitDto);
        log.info("StatsController method addHit: added hit={}", respServiceEndpointHitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respServiceEndpointHitDto);
    }


    @GetMapping(path = "/stats")
    public ResponseEntity<List<ViewStatsDto>> getStats(
            @RequestParam(name = "start", defaultValue = "2022-01-01 00:00:00") String start,
            @RequestParam(name = "end", defaultValue = "2035-01-01 00:00:00") String end,
            @RequestParam(name = "uris", defaultValue = "all") String[] uris,
            @Validated(Boolean.class) @RequestParam(name = "unique", defaultValue = "false") boolean unique,
            HttpServletRequest servletRequest
    ) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        log.info("StatsController starting method getStats: UriParameters[ uris={}, uniqueIp={} ]",
                servletRequest.getParameterValues("uris"), servletRequest.getParameter("unique"));

        Parameters parameters = Parameters.builder()
                .uris(uris)
                .start(LocalDateTime.parse(start, formatter))
                .end(LocalDateTime.parse(end, formatter))
                .unique(unique)
                .build();

        List<ViewStatsDto> stats = service.getStats(parameters);
        int hits = 0;

        for (ViewStatsDto stat : stats) {
            hits += stat.getHits();
        }

        log.info("StatsController method getStats complete and find {} uri with {} hits",
                stats.size(), hits);

        return ResponseEntity.ok().body(stats);
    }

}
