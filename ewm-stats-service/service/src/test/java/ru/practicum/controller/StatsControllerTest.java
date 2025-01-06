package ru.practicum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.StatisticServiceImpl;
import util.DataUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
class StatsControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private StatisticServiceImpl service;
    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {


    }

    @Test
    @DisplayName(value = "Тест добавления в статистику обращений по эндпоинтам")
    void givenEndpointDto_whenAddedNewEndpointDto_thenSuccessStatusAndBody() throws Exception {
        int countAdd = 10;  // имитируем добавление множества ендпоинтов в статистику

        for (int i = 1; i <= countAdd; i++) {

            // given
            String uri = "/event/" + i;
            EndpointHitDto dtoReq = DataUtils.makeNewEndpointHitDto("/event/" + i);
            EndpointHitDto dtoResp = DataUtils.makeEndpointHitDtoWithId(i, "/event/" + i);

            // when
            when(service.addHit(Mockito.any()))
                    .thenReturn(dtoResp);

            // then
            mvc.perform(post("/hit")
                            .content(objectMapper.writeValueAsString(dtoReq))
                            .characterEncoding(StandardCharsets.UTF_8)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id", is(i)))
                    .andExpect(jsonPath("$.uri", is(uri)))
                    .andExpect(jsonPath("$.app", is("ewm-main-service")))
                    .andExpect(jsonPath("$.ip", is("10.0.0.127")))
                    .andExpect(jsonPath("$.timestamp", is(dtoResp.getTimestamp())));

        }

    }

    @Test
    @DisplayName(value = "Тест получения статистики обращений по эндпоинтам")
    void givenParametersStats_whenGetStats_thenSuccessStatusAndBody() throws Exception {
        int minHits = 10;  // число минимальных обращений к эндпоинту
        int events = 50;  // число эндпоинтов
        // given получаем массив uri
        StringBuilder urisString = new StringBuilder();
        String[] uris = new String[events];
        for (int i = 1; i <= events; i++) {
            urisString.append("/events/").append(i).append(",");
            uris[i - 1] = "/events/" + i;
        }
        urisString.deleteCharAt(urisString.length() - 1); // удаляем последнюю запятую

        // when мокируем ответ сервиса
        List<ViewStatsDto> viewStats = DataUtils.getSortedListViewStats(uris, minHits);
        // считаем что уникальных запросов меньше в 2 раза
        when(service.getStats(DataUtils.getUniqueIpParams(
                LocalDateTime.parse("2020-05-05 00:00:00", DataUtils.formatter),  // start
                LocalDateTime.parse("2035-05-05 00:00:00", DataUtils.formatter),  // end
                uris)))
                .thenReturn(viewStats);
        // запросы без uris
        when(service.getStats(DataUtils.getUniqueIpParamsWithoutUris(
                LocalDateTime.parse("2020-05-05 00:00:00", DataUtils.formatter),  // start
                LocalDateTime.parse("2035-05-05 00:00:00", DataUtils.formatter))))  // end
                .thenReturn(viewStats);

        // и для не уникальных повторно мокируем, при чем hits будет для них в 2 раза больше
        minHits = minHits + minHits;
        viewStats = DataUtils.getSortedListViewStats(uris, minHits);
        when(service.getStats(DataUtils.getNonUniqueIpParams(
                LocalDateTime.parse("2020-05-05 00:00:00", DataUtils.formatter),  // start
                LocalDateTime.parse("2035-05-05 00:00:00", DataUtils.formatter),  // end
                uris)))
                .thenReturn(viewStats);

        // then
        // unique ip
        mvc.perform(get("/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&uris=" + urisString +
                        "&unique=true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].app").value(hasItem("ewm-main-service")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/1")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + events / 2)))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + (events - 1))))
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits / 2)))
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits / 2 + (events - 1))));

        // not unique ip
        mvc.perform(get("/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&uris=" + urisString +
                        "&unique=false")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].app").value(hasItem("ewm-main-service")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/1")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + events / 2)))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + (events - 1))))
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits)))  // отличие только в количестве hits
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits + (events - 1))));


        // not correct unique parameter
        mvc.perform(get("/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&uris=" + urisString +
                        "&unique=abrakadabra")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // without uris param
        mvc.perform(get("/stats?start=2020-05-05 00:00:00&end=2035-05-05 00:00:00&unique=true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].app").value(hasItem("ewm-main-service")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/1")))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + events / 2)))
                .andExpect(jsonPath("$[*].uri").value(hasItem("/events/" + (events - 1))))
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits / 2)))
                .andExpect(jsonPath("$[*].hits").value(hasItem(minHits / 2 + (events - 1))));

    }

}