package practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import practicum.model.User;
import ru.practicum.dto.user.UserCreateRequestDto;
import ru.practicum.dto.user.UserCreateResponseDto;
import ru.practicum.dto.user.UserResponseDto;
import ru.practicum.dto.user.UserUpdateRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    User toModelFromCreateRequestDto(UserCreateRequestDto userDto);

    User toModelFromUpdateRequestDto(UserUpdateRequestDto userDto);

    UserCreateResponseDto toCreateResponseDto(User user);

    UserResponseDto toResponseDto(User user);


}
