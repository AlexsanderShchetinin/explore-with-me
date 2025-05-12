package practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import practicum.model.User;
import ru.practicum.dto.user.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    User toModelFromCreateRequestDto(UserCreateRequestDto userDto);

    User toModelFromUpdateRequestDto(UserUpdateRequestDto userDto);

    UserCreateResponseDto toCreateResponseDto(User user);

    UserResponseDto toResponseDto(User user);

    UserShortResponseDto toShortResponseDto(User user);

}
