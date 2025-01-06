package util;

import ewm_stats_dto.dto.EndpointHitDto;
import ewm_stats_dto.dto.Parameters;
import ewm_stats_dto.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class DataUtils {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHitDto makeNewEndpointHitDto(String uri) {
        return EndpointHitDto.builder()
                .app("ewm-main-service")
                .uri(uri)
                .ip("10.0.0.127")
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }

    public static EndpointHitDto makeEndpointHitDtoWithId(int id, String uri) {
        return EndpointHitDto.builder()
                .id(id)
                .app("ewm-main-service")
                .uri(uri)
                .ip("10.0.0.127")
                .timestamp(LocalDateTime.now().format(formatter))
                .build();
    }


    public static List<ViewStatsDto> getSortedListViewStats(String[] uris, int minHits) {

        List<ViewStatsDto> viewStats = new java.util.ArrayList<>(List.of());
        for (String uri : uris) {
            viewStats.add(ViewStatsDto.builder()
                    .app("ewm-main-service")
                    .uri(uri)
                    .hits(minHits)
                    .build());
            minHits++;
        }

        return viewStats.stream()
                .sorted(Comparator.comparing(ViewStatsDto::getHits).reversed())
                .toList();
    }

    public static Parameters getUniqueIpParams(LocalDateTime start, LocalDateTime end, String[] uris) {
        return Parameters.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(true)
                .build();
    }

    public static Parameters getNonUniqueIpParams(LocalDateTime start, LocalDateTime end, String[] uris) {
        return Parameters.builder()
                .start(start)
                .end(end)
                .uris(uris)
                .unique(false)
                .build();
    }

    public static Parameters getUniqueIpParamsWithoutUris(LocalDateTime start, LocalDateTime end) {
        return Parameters.builder()
                .start(start)
                .end(end)
                .uris(new String[]{"all"})
                .unique(true)
                .build();
    }

}
