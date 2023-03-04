package ru.practicum.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.RemoteRef;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        user.setRegistrationDate(Timestamp.from(Instant.now()));
        user.setState(States.ACTIVE);
        return repository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public List<UserShort> findByEmail(String emailSearch) {
        emailSearch = Optional.ofNullable(emailSearch).orElse("");
        return repository.findAllByEmailContainingIgnoreCase(emailSearch);
    }

    @Override
    public void deleteUser(long userId) {
        repository.deleteById(userId);
    }
}
