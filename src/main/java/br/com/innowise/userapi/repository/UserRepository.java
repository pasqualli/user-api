package br.com.innowise.userapi.repository;

import br.com.innowise.userapi.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,Integer> {
}