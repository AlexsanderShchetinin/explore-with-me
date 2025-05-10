package practicum.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practicum.service.UserService;

import java.util.UUID;

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

    @GetMapping(path = "/{id}", name = "получить пользователя")
    public ResponseEntity<UserDto> getByUuid(@PathVariable UUID uuid){
        //TODO AOP create comments
        return ResponseEntity.ok().body(userService.getByUuid(uuid));
    }



    @PostMapping(path = "/auth")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){
        //TODO AOP create comments
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PutMapping(path = "/auth/{uuid}")
    public ResponseEntity<UserDto> update(@RequestBody UserDto updatedUserDto){
        //TODO AOP create comments
        return ResponseEntity.ok(userService.update(updatedUserDto));
    }

    @DeleteMapping(path = "/auth/softDelete/{uuid}")
    public ResponseEntity<Void> softDelete(@PathVariable UUID uuid){
        //TODO AOP create comments
        userService.softDeleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(path = "/auth/hardDelete/{uuid}")
    public ResponseEntity<Void> hardDelete(@PathVariable UUID uuid){
        //TODO AOP create comments
        userService.hardDeleteByUuid(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
