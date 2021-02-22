package service;

import domain.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    boolean register(User user);
    boolean login(User user);
    boolean logout();
    boolean updateUser(User user);
    boolean withdrawal(User user);
}
