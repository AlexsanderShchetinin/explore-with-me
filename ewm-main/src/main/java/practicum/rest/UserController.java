package practicum.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicum.service.UserService;
import ru.practicum.dto.user.UserCreateRequestDto;
import ru.practicum.dto.user.UserCreateResponseDto;
import ru.practicum.dto.user.UserResponseDto;
import ru.practicum.dto.user.UserUpdateRequestDto;

@RestController
@Slf4j
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/random/{count}")
    public ResponseEntity<Integer> createRandomUsers(@PathVariable Integer count){
        return ResponseEntity.ok(userService.createRandomUsers(count));
    }

    @GetMapping(path = "/getCount")
    public ResponseEntity<Long> getUserCountDB(){
        return ResponseEntity.ok().body(userService.getUserCount());
    }

    @GetMapping(path = "/{id}", name = "получить базовую информацию пользователя")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        //TODO AOP create comments
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @GetMapping(path = "/auth/{id}", name = "получить подробную информацию пользователя")
    public ResponseEntity<UserResponseDto> authGetById(@PathVariable Long id){
        //TODO AOP create comments
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<UserCreateResponseDto> create(@RequestBody UserCreateRequestDto userDto){
        //TODO AOP create comments
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PutMapping(path = "/auth/{id}")
    public ResponseEntity<UserResponseDto> update(@RequestBody UserUpdateRequestDto updatedUserDto){
        //TODO AOP create comments
        return ResponseEntity.ok(userService.update(updatedUserDto));
    }

    @PutMapping(path = "/admin/confirm/{id}")
    public ResponseEntity<UserResponseDto> confirm(@RequestBody UserUpdateRequestDto updatedUserDto){
        //TODO AOP create comments
        return ResponseEntity.ok(userService.update(updatedUserDto));
    }

    @DeleteMapping(path = "/auth/softDelete/{uuid}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id){
        //TODO AOP create comments
        userService.softDeleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(path = "/auth/hardDelete/{uuid}")
    public ResponseEntity<Void> hardDelete(@PathVariable Long id){
        //TODO AOP create comments
        userService.hardDeleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
