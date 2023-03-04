package ru.practicum.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserMapper::makeUserDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public UserDto saveNewUser(@RequestBody CreateUserDto userDto) {
        return UserMapper.makeUserDto(userService.saveUser(UserMapper.makeUser(userDto)));
    }

    @PatchMapping
    public UserDto updateUser(@RequestBody CreateUserDto userDto) {
        return UserMapper.makeUserDto(userService.updateUser(UserMapper.makeUser(userDto)));
    }

    @GetMapping("/search")
    public List<UserShort> findByEmail(@RequestParam(required = false) String emailSearch) {
        return userService.findByEmail(emailSearch);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {

    }

}
