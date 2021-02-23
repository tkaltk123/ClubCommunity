package service;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import repository.UserMapper;
import util.MyUtil;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) {
        HttpSession session = MyUtil.getSession();
        //로그인 상태일 경우
        if(session.getAttribute("user-id")!=null)
            throw new RuntimeException("already logged in.");
        //삭제된 회원의 아이디나 닉네임도 중복 불가
        if(userMapper.getUserByAccountId(user.getAccount_id()) != null)
            throw new RuntimeException("account id conflict.");
        if(userMapper.getUserByNickName(user.getNick_name()) != null)
            throw new RuntimeException("nickname conflict");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); //비밀번호 암호화
        userMapper.insertUser(user);
        User createdUser = userMapper.getUserByAccountId(user.getAccount_id());
        session.setAttribute("user-id",createdUser.getId());
    }

    @Override
    public void login(User user) {
        HttpSession session = MyUtil.getSession();
        if(session.getAttribute("user-id")!=null)
            throw new RuntimeException("already logged in.");
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        if(userInfo==null||//id가 일치하는 사용자가 없거나
           userInfo.isDeleted()||   //이미 탈퇴한 회원이거나
           !BCrypt.checkpw(user.getPassword(), userInfo.getPassword())) //비밀번호가 잘못되었을 경우
            throw new RuntimeException("wrong account id or password.");
        session.setAttribute("user-id",userInfo.getId());
    }

    @Override
    public void logout() {
        HttpSession session = MyUtil.getSession();
        if(session.getAttribute("user-id")==null)
            throw new RuntimeException("already logged out.");
        session.invalidate();
    }
    @Override
    public void updateUser(User user) {
        Long userId = (Long)MyUtil.getSession().getAttribute("user-id");
        //세션이 없을 경우
        if(userId==null)
            throw new RuntimeException("is wrong session.");
        User userInfo = userMapper.getUserById(userId);
        User sameAccountUser = userMapper.getUserByAccountId(user.getAccount_id());
        //자신이 아닌 중복된 계정이 존재할 경우
        if(sameAccountUser != null &&
                !sameAccountUser.getAccount_id().equals(userInfo.getAccount_id()))
            throw new RuntimeException("account id conflict.");
        User sameNicknameUser = userMapper.getUserByNickName(user.getNick_name());
        //자신이 아닌 중복된 닉네임이 존재할 경우
        if(sameNicknameUser != null &&
           !sameNicknameUser.getId().equals(userInfo.getId()))
            throw new RuntimeException("nickname conflict.");
        user.setId(userId);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userMapper.updateUser(user);
    }

    @Override
    public void withdrawal(User user) {
        Long userId = (Long)MyUtil.getSession().getAttribute("user-id");
        //세션이 없을 경우
        if(userId==null)
            throw new RuntimeException("is wrong session.");
        User userInfo = userMapper.getUserById(userId);
        //비밀번호가 틀릴 경우
        if(!BCrypt.checkpw(user.getPassword(), userInfo.getPassword()))
            throw new RuntimeException("is wrong password.");
        userMapper.softDeleteUser(user);
        logout();
    }
}
