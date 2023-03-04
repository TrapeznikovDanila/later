package ru.practicum.user;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    private String email;
    private String firstName;
    private String lastName;
}
