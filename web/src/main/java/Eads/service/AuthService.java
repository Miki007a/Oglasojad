package Eads.service;

import Eads.model.User;

public interface AuthService {
    User login(String username, String password);
}
