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
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        session.setAttribute("user-id",userInfo.getId());
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
    //세션과 db에 저장된 정보를 비교해 유효성 검사
    private boolean isWrongSession(User user){
        HttpSession session = MyUtil.getSession();
        Long userId = (Long)session.getAttribute("user-id");
        //세션이 없거나 잘못된 계정 이거나 세션과 db의 id가 다를 경우
        return userId == null || user == null || !userId.equals(user.getId());
    }
    @Override
    public void updateUser(User user) {
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        if(isWrongSession(userInfo))
            throw new RuntimeException("is wrong session.");
        User sameNicknameUser = userMapper.getUserByNickName(user.getNick_name());
        //자신이 아닌 중복된 닉네임이 존재할 경우
        if(sameNicknameUser != null &&
           !sameNicknameUser.getId().equals(userInfo.getId()))
            throw new RuntimeException("nickname conflict.");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userMapper.updateUser(user);
    }

    @Override
    public void withdrawal(User user) {
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        //잘못된 세션이거나 비밀번호가 틀릴 경우
        if(isWrongSession(userInfo))
            throw new RuntimeException("is wrong session.");
        if(!BCrypt.checkpw(user.getPassword(), userInfo.getPassword()))
            throw new RuntimeException("is wrong password.");
        userMapper.softDeleteUser(user);
        logout();
    }
}
