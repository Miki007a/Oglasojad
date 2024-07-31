package Eads.service;

import Eads.model.Role;
import Eads.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
    User findByUsername(String username);
}
