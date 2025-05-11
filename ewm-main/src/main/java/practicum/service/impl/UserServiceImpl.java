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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

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
    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new NotFoundException("not found user by uuid=" + id)
                );
        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getByListId(List<Long> ids) {
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
        User userRep = userRepository.findById(updatedUserDto.getId())
                .orElseThrow(
                        () -> new NotFoundException("not found user by uuid=" + updatedUserDto.getId())
                );
        User user = userMapper.toModelFromUpdateRequestDto(updatedUserDto);
        for (Field field : userRep.getClass().getDeclaredFields()) {
            try {
                Field userClassField = user.getClass().getDeclaredField(field.getName());
                userClassField.setAccessible(true);
                field.setAccessible(true);
                if((userClassField.get(user) != null) && (!userClassField.get(user).equals(field.get(userRep)))){
                    // TODO log updating field
                    field.set(userRep, userClassField.get(user));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        User updatedUser = userRepository.save(userRep);
        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    public void softDeleteById(Long id) {

    }

    @Override
    public void hardDeleteById(Long id) {

    }
}
