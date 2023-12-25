package br.com.innowise.userapi.controller;

import br.com.innowise.userapi.dto.UserDto;
import br.com.innowise.userapi.model.User;
import br.com.innowise.userapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    Mono<User> create(@RequestBody Mono<UserDto> userDto) {
        return userService.create(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    Mono<ResponseEntity<User>> retrieve(@PathVariable int userId) {
        return userService.retrieve(userId)
                .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    Mono<ResponseEntity<User>> update(@PathVariable int userId, @RequestBody Mono<UserDto> userDto) {
        return userService.update(userId, userDto).map(ResponseEntity::ok).defaultIfEmpty(
                ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    Mono<Void> delete(@PathVariable int userId) {
        return userService.delete(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/")
    Flux<User> list() {
        return userService.list();
    }
}