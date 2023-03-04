package ru.practicum.user;

import java.time.LocalDateTime;

public class UserMapper {
    public static User makeUser(CreateUserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail()).build();
    }

    public static UserDto makeUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .registrationDate((user.getRegistrationDate()))
                .state(user.getState())
                .build();
    }
}
