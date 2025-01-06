package ru.practicum.client;


import ru.practicum.dto.EndpointHitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Service
public class StatsClient extends RestClient {


    @Autowired
    public StatsClient(@Value("${ewm-main-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> getStats(String startEncoding, String endEncoding, String[] uris, boolean unique) {

        Map<String, Object> params = Map.of(
                "start", startEncoding,
                "end", endEncoding,
                "uris", uris,
                "unique", unique
        );

        return get("?start={start}&end={end}&uris={uris}&unique={unique}", params);
    }


    public ResponseEntity<Object> addHit(EndpointHitDto endpointHitDto) {
        return post("", endpointHitDto);
    }

}
