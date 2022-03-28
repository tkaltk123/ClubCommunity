package repository;

import domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    //insert
    void insertUser(User user);
    //select
    User getUserById(Long id);
    User getUserByAccountId(String account_id);
    User getUserByNickName(String nick_name);
    //update
    void updateUser(User user);
    void softDeleteUser(Long id);
}
