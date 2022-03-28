package service;

import domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    void register(User user);
    void login(User user);
    void logout();
    void updateUser(User user);
    void withdrawal(User user);
}
