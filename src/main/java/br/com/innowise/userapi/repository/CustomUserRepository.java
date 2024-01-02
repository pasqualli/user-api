package br.com.innowise.userapi.repository;

import br.com.innowise.userapi.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;

@Component
@RequiredArgsConstructor
public class CustomUserRepository {

    private final R2dbcEntityTemplate template;
    private final DatabaseClient client;

    private static final String SELECT_QUERY="SELECT U.ID U_ID, U.FIRST_NAME U_FIRST_NAME, U.LAST_NAME  U_LAST_NAME FROM USERS U";


    public Mono<User> save(User u ){
        String insert = "INSERT INTO users(FIRST_NAME, LAST_NAME) VALUES (:fn, :ls)";

        return Mono.justOrEmpty(u)
                .flatMap(user -> client.sql(insert)
                        .bind("fn", u.getFirstName())
                        .bind("ls", u.getLastName())
                        .fetch().rowsUpdated())
                .thenReturn(u);
    }

    public Mono<User> update(User u ){
        String update = "UPDATE users SET FIRST_NAME = :fn, LAST_NAME = :ls WHERE id = :id";

        return this.client.sql(update)
                .bind("fn", u.getFirstName())
                .bind("ls", u.getLastName())
                .bind("id", u.getId())
                .fetch().first()
                .thenReturn(u);
    }


    public Flux<User> findAll() {
        String query = String.format("%s ORDER BY U.ID", SELECT_QUERY);

        return client.sql(query)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("U_ID"))
                .flatMap(User::fromRows);
    }

    public Mono<User> findById(long id) {
        String query = String.format("%s WHERE u.id = :id", SELECT_QUERY);

        return client.sql(query)
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(result -> result.get("u_id"))
                .flatMap(User::fromRows)
                .singleOrEmpty();
    }

    public Mono<Void> deleteById(long id) {
        String query = "DELETE FROM USERS WHERE u.id = :id";
        return this.template.delete(Query.query(where("id").is(id)), User.class).then();
    }
}
