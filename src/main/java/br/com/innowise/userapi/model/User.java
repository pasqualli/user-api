package br.com.innowise.userapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Table("users")
public class User {

    @Id
    private int id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Mono<User> fromRows(List<Map<String, Object>> rows) {
        return Mono.just(User.builder()
                .id((Integer.parseInt(rows.get(0).get("U_ID").toString())))
                .firstName((String) rows.get(0).get("U_FIRST_NAME"))
                        .lastName((String) rows.get(0).get("U_LAST_NAME"))

                .build());
    }
}