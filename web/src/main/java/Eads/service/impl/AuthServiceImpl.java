package Eads.service.impl;

import Eads.model.exceptions.InvalidArgumentsException;
import Eads.service.AuthService;
import Eads.model.User;
import Eads.model.exceptions.InvalidUserCredentialsException;

import Eads.repository.jpa.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }

}
