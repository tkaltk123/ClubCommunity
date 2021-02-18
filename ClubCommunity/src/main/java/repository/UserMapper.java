package repository;

import domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    public void insertUser(User user);
    public User getUserByAccountId(String acid);
    public User getUserById(long id);
    public User getUserByNickName(String nick_name);
}
