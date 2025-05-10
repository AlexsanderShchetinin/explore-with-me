package practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practicum.exception.NotFoundException;
import practicum.exception.UserCreateException;
import practicum.mapper.UserMapperImpl;
import practicum.model.User;
import practicum.model.UserRole;
import practicum.repository.UserJpaRepository;
import practicum.service.UserService;
import practicum.util.UserUtil;
import ru.practicum.dto.user.UserCreateRequestDto;
import ru.practicum.dto.user.UserCreateResponseDto;
import ru.practicum.dto.user.UserResponseDto;
import ru.practicum.dto.user.UserUpdateRequestDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userRepository;
    private final UserMapperImpl userMapper;
    private final UserUtil util;

    @Override
    public Long getUserCount() {
        return userRepository.count();
    }

    @Override
    public Integer createRandomUsers(Integer count) {
        return userRepository.saveAll(util.getRandomUsers(count)).size();
    }

    @Override
    public UserResponseDto getByUuid(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(
                        () -> new NotFoundException("not found user by uuid=" + uuid)
                );
        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getByListId(List<UUID> uuids) {
        return List.of();
    }

    @Override
    public UserCreateResponseDto create(UserCreateRequestDto userDto) {
        // Проверка роли
        long result = Arrays.stream(UserRole.values())
                .map(Enum::name)
                .filter(role -> role.equalsIgnoreCase(userDto.getRole()))
                .peek(userDto::setRole)
                .count();
        if(result == 0){
            throw new UserCreateException("Некорректная роль пользователя, необходимые: " + Arrays.toString(UserRole.values()));
        }

        User user = userRepository.save(userMapper.toModelFromCreateRequestDto(userDto));
        return userMapper.toCreateResponseDto(user);
    }

    @Override
    public UserResponseDto update(UserUpdateRequestDto updatedUserDto) {
        return null;
    }

    @Override
    public void softDeleteByUuid(UUID uuid) {

    }

    @Override
    public void hardDeleteByUuid(UUID uuid) {

    }
}
