package service;

import domain.User;

import java.util.List;

public interface UserService {
    boolean register(User user);
    boolean login(User user);
}
