package br.com.innowise.userapi.service;

import br.com.innowise.userapi.dto.UserDto;
import br.com.innowise.userapi.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> create(Mono<UserDto> userDto);

    Mono<User> retrieve(int userId);

    Mono<User> update(int userId, Mono<UserDto> userDto);

    Mono<Void> delete(int userId);

    Flux<User> list();
}
