package br.com.innowise.userapi.utils;


import br.com.innowise.userapi.dto.UserDto;
import br.com.innowise.userapi.model.User;

public class UserUtils {
    public static User toUser(UserDto userDto) {
        return new User(
                userDto.getFirstName()!=null ? userDto.getFirstName() : null,
                userDto.getLastName() !=null ? userDto.getLastName() : null);
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getFirstName(), user.getLastName());
    }
}