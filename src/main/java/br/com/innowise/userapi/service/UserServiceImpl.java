package br.com.innowise.userapi.service;


import br.com.innowise.userapi.dto.UserDto;
import br.com.innowise.userapi.model.User;
import br.com.innowise.userapi.repository.CustomUserRepository;
import br.com.innowise.userapi.repository.UserRepository;
import br.com.innowise.userapi.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;


    @Override
    public Mono<User> create(Mono<UserDto> userDto) {
        return userDto.map(UserUtils::toUser).flatMap(customUserRepository::save);
    }

    @Override
    public Mono<User> retrieve(int userId) {
        return customUserRepository.findById(userId);
    }

    @Override
    public Mono<User> update(int userId, Mono<UserDto> userDto) {
        return userRepository.findById(userId)
                .flatMap(user -> userDto
                        .map(UserUtils::toUser)
                        .doOnNext(u -> u.setId(userId)))
                .flatMap(customUserRepository::update);
    }

    @Override
    public Mono<Void> delete(int userId) {
        return customUserRepository.deleteById(userId);
    }

    @Override
    public Flux<User> list() {
        return customUserRepository.findAll();
    }
}