package practicum.service;

import ru.practicum.dto.user.UserCreateRequestDto;
import ru.practicum.dto.user.UserCreateResponseDto;
import ru.practicum.dto.user.UserResponseDto;
import ru.practicum.dto.user.UserUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDto getByUuid(UUID uuid);

    List<UserResponseDto> getByListId(List<UUID> uuids);

    UserCreateResponseDto create(UserCreateRequestDto userDto);

    UserResponseDto update(UserUpdateRequestDto updatedUserDto);

    void softDeleteByUuid(UUID uuid);

    void hardDeleteByUuid(UUID uuid);

    Integer createRandomUsers(Integer count);

    Long getUserCount();

}
