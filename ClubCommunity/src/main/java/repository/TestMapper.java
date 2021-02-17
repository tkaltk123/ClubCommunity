package repository;

import domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    List<User> getUsers();
}
