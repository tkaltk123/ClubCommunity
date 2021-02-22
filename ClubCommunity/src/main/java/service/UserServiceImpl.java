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
    public boolean register(User user) {
        HttpSession session = MyUtil.getSession();
        //로그인 상태일 경우
        if(session.getAttribute("user-id")!=null)
            return false;
        //삭제된 회원의 아이디나 닉네임도 중복 불가
        if(userMapper.getUserByAccountId(user.getAccount_id()) != null ||
           userMapper.getUserByNickName(user.getNick_name()) != null)
            return false;
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt())); //비밀번호 암호화
        userMapper.insertUser(user);
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        session.setAttribute("user-id",userInfo.getId());
        System.out.println("회원 가입 성공");
        return true;
    }

    @Override
    public boolean login(User user) {
        HttpSession session = MyUtil.getSession();
        if(session.getAttribute("user-id")!=null)
            return false;
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        if(userInfo==null||//id가 일치하는 사용자가 없거나
           userInfo.isDeleted()||   //이미 탈퇴한 회원이거나
           !BCrypt.checkpw(user.getPassword(), userInfo.getPassword())) //비밀번호가 잘못되었을 경우
            return false;
        session.setAttribute("user-id",userInfo.getId());
        System.out.println("로그인 성공");
        return true;
    }

    @Override
    public boolean logout() {
        HttpSession session = MyUtil.getSession();
        if(session.getAttribute("user-id")==null)
            return false;
        session.invalidate();
        System.out.println("로그아웃 성공");
        return true;
    }
    //세션과 db에 저장된 정보를 비교해 유효성 검사
    private boolean isWrongSession(User user){
        HttpSession session = MyUtil.getSession();
        Long userId = (Long)session.getAttribute("user-id");
        //세션이 없거나 잘못된 계정 이거나 세션과 db의 id가 다를 경우
        return userId == null || user == null || !userId.equals(user.getId());
    }
    @Override
    public boolean updateUser(User user) {
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        //잘못된 세션이거나 닉네임이 중복될 경우
        if(isWrongSession(userInfo) || userMapper.getUserByNickName(user.getNick_name()) != null)
            return false;
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userMapper.updateUser(user);
        System.out.println("정보 수정 성공");
        return true;
    }

    @Override
    public boolean withdrawal(User user) {
        User userInfo = userMapper.getUserByAccountId(user.getAccount_id());
        //잘못된 세션이거나 비밀번호가 틀릴 경우
        if(isWrongSession(userInfo) || !BCrypt.checkpw(user.getPassword(), userInfo.getPassword()))
            return false;
        userMapper.softDeleteUser(user);
        System.out.println("회원 탈퇴 성공");
        logout();
        return true;
    }
}
