package ru.job4j.cars.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.user.UserRepository;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }
}
