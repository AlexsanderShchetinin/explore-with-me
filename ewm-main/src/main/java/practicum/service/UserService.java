package practicum.service;

import ru.practicum.dto.user.*;

import java.util.List;

public interface UserService {

    UserResponseDto getById(Long id);

    UserShortResponseDto getShortInfoById(Long id);

    List<UserResponseDto> getByListId(List<Long> ids);

    UserCreateResponseDto create(UserCreateRequestDto userDto);

    UserResponseDto update(UserUpdateRequestDto updatedUserDto);

    void softDeleteById(Long id);

    void hardDeleteById(Long id);

    Integer createRandomUsers(Integer count);

    Long getUserCount();

}
