package practicum.service;

import ru.practicum.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto getByUuid(UUID uuid);

    List<UserDto> getByListId(List<UUID> uuids);

    UserDto create(UserDto userDto);

    UserDto update(UserDto updatedUserDto);

    void softDeleteByUuid(UUID uuid);

    void hardDeleteByUuid(UUID uuid);

    Integer createRandomUsers(Integer count);

    Long getUserCount();

}
