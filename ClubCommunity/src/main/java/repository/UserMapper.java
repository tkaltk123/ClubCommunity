package repository;

import domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    public void insertUser(User user);
    public User getUserById(Long id);
    public User getUserByAccountId(String account_id);
    public User getUserByNickName(String nick_name);
    public void updateUser(User user);
    public void softDeleteUser(Long id);
}
