package practicum.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicum.service.UserService;
import ru.practicum.dto.user.*;

/**
 * REST Controller для взаимодействия с пользователями приложения
 */
@RestController
@Slf4j
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Dev-endpoint для создания рандомных тестовых user и наполнения ими БД
     * Обязательно удалить в продакшене!!!
     * @param count количество создаваемых пользователей
     * @return число сохраненных пользователей в БД
     */
    @PostMapping(path = "/random/{count}")
    public ResponseEntity<Integer> createRandomUsers(@PathVariable Integer count){
        return ResponseEntity.ok(userService.createRandomUsers(count));
    }

    /**
     * Dev-endpoint для получения из БД числа всех user
     * Обязательно удалить в продакшене либо настроить авторизацию для админов!!!
     * @return число сохраненных пользователей в БД
     */
    @GetMapping(path = "/getCount")
    public ResponseEntity<Long> getUserCountDB(){
        return ResponseEntity.ok().body(userService.getUserCount());
    }

    /**
     * Эндпоинт получения ограниченной информации о пользователе.
     * Используется для неавторизованных пользователей
     * @param id идентификатор пользователя типа Long
     * @return {@link UserShortResponseDto} - Dto с ограниченной информацией о пользователе
     */
    @GetMapping(path = "/{id}", name = "получить базовую информацию пользователя")
    public ResponseEntity<UserShortResponseDto> getShortInfoById(@PathVariable Long id){
        //TODO AOP create comments
        return ResponseEntity.ok().body(userService.getShortInfoById(id));
    }

    /**
     * Эндпоинт получения базовой информации о пользователе.
     * Используется для авторизованных пользователей и администраторов
     * @param id идентификатор пользователя типа Long
     * @return {@link UserResponseDto} с базовой информацией о пользователе
     */
    @GetMapping(path = "/auth/{id}", name = "получить подробную информацию пользователя")
    public ResponseEntity<UserResponseDto> authGetById(@PathVariable Long id){
        //TODO AOP create comments
        return ResponseEntity.ok().body(userService.getById(id));
    }

    /**
     * Эндпоинт создания учетной записи пользователя с внесением информации в Keycloak
     * @param userDto {@link UserCreateRequestDto}
     * @return {@link UserCreateResponseDto}
     */
    @PostMapping
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody UserCreateRequestDto userDto){
        //TODO AOP create comments
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }


    /**
     * Эндпоинт обновления собственных учетных данных пользователя
     * В {@link practicum.config.SecurityConfig} должен быть прописан механизм получения информациии
     * по пользователю из JWT Token с целью идентификации именно того пользователя который меняет свои данные
     * @param updatedUserDto {@link UserUpdateRequestDto}
     * @return {@link UserResponseDto}
     */
    @PutMapping(path = "/auth/{id}")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateRequestDto updatedUserDto){
        //TODO AOP create comments
        return ResponseEntity.ok(userService.update(updatedUserDto));
    }

    /**
     * Эндпоинт обновления учетных данных пользователя из под УЗ администратора
     * @param updatedUserDto {@link UserUpdateRequestDto}
     * @return {@link UserResponseDto}
     */
    @PutMapping(path = "/admin/{id}")
    public ResponseEntity<UserResponseDto> updateByAdmin(@RequestBody UserUpdateRequestDto updatedUserDto){
        //TODO AOP create comments
        return ResponseEntity.ok(userService.update(updatedUserDto));
    }

    /**
     * Эндпоинт проставления метки удаления пользователя.
     * Доступен только для пользователя - владельца своей УЗ.
     * В {@link practicum.config.SecurityConfig} должен быть прописан механизм получения информациии
     * по пользователю из JWT Token с целью идентификации пользователя который удаляет именно себя а не другого пользователя
     * @param id идентификатор пользователя типа Long
     * @return Void со статусом 204(NO_CONTENT) при успешном проставлении метки удаления
     */
    @DeleteMapping(path = "/auth/softDelete/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id){
        //TODO AOP create comments
        userService.softDeleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Эндпоинт полного удаления пользователя из БД.
     * доступен только под ролью администратора!
     * @param id идентификатор пользователя типа Long
     * @return Void со статусом 204(NO_CONTENT) при успешном удалении пользователя из БД
     */
    @DeleteMapping(path = "/admin/hardDelete/{uuid}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id){
        //TODO AOP create comments
        userService.hardDeleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
