package practicum.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practicum.model.User;
import practicum.model.UserRole;
import practicum.repository.UserJpaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserJpaRepository repository;

    public List<User> getRandomUsers(int count){
        long firstCount = repository.count();
        List<User> userList = new ArrayList<>();
        for (long i = firstCount; i < (firstCount+count); i++) {
            userList.add(
                    User.builder()
                            .firstname("Random firstname_" + i)
                            .lastname("Random lastname_" + i)
                            .email("Random_" + i + "@mail.ru")
                            .role(UserRole.UNAUTHORIZED_USER)
                            .created(LocalDateTime.now())
                            .updated(LocalDateTime.now())
                            .flagDelete(false)
                            .adminConfirm(false)
                            .build()
            );
        }
        return userList;
    }

}
