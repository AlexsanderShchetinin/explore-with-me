package ewm_stats_service.mapper;


import ewm_stats_dto.dto.EndpointHitDto;
import ewm_stats_service.model.EndpointHit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {


    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "StringToLocalDateTimeFormat")
    EndpointHit toModel(EndpointHitDto dto);

    @Mapping(target = "timestamp", source = "timestamp", qualifiedByName = "LocalDateTimeFormatToString")
    EndpointHitDto toDto(EndpointHit model);


    @Named("StringToLocalDateTimeFormat")
    default LocalDateTime stringToLocalDateTimeFormat(String string) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(string, formatter);
    }

    @Named("LocalDateTimeFormatToString")
    default String LocalDateTimeFormatToString(LocalDateTime dateTime) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }


}
