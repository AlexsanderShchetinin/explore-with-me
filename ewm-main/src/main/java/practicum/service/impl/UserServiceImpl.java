package practicum.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practicum.exception.NotFoundException;
import practicum.mapper.UserMapperImpl;
import practicum.model.User;
import practicum.repository.UserJpaRepository;
import practicum.service.UserService;
import practicum.util.UserUtil;
import ru.practicum.dto.UserDto;

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
    public UserDto getByUuid(UUID uuid) {
        User user = userRepository.findById(uuid)
                .orElseThrow(
                        () -> new NotFoundException("not found user by uuid=" + uuid)
                );
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> getByListId(List<UUID> uuids) {
        return List.of();
    }

    @Override
    public UserDto create(UserDto userDto) {

        return null;
    }

    @Override
    public UserDto update(UserDto updatedUserDto) {
        return null;
    }

    @Override
    public void softDeleteByUuid(UUID uuid) {

    }

    @Override
    public void hardDeleteByUuid(UUID uuid) {

    }
}
