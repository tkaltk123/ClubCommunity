package service;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import repository.UserMapper;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean register(User user) {
        User sameAccountIdUser = userMapper.getUserByAccountId(user.getAccount_id());
        User sameNickNameUser = userMapper.getUserByNickName(user.getNick_name());
        if (sameAccountIdUser != null || sameNickNameUser!=null)    //id나 닉네임이 중복될 경우
            return false;
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());    //비밀번호 암호화
        user.setPassword(hashedPassword);
        userMapper.insertUser(user);
        return true;
    }

    @Override
    public boolean login(User user) {
        User selectUser = userMapper.getUserByAccountId(user.getAccount_id());
        return selectUser != null && BCrypt.checkpw(user.getPassword(), selectUser.getPassword());
    }
}
