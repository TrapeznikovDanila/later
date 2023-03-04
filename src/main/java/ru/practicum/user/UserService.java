package ru.practicum.user;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

interface UserService {
    List<User> getAllUsers();
    User saveUser(User user);
    User updateUser(User user);
    List<UserShort> findByEmail(String emailSearch);
    void deleteUser(long userId);
}
