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
    //로그인 중이거나 계정이나 닉네임이 중복될 경우 예외 발생
    //회원가입이 끝나면 자동으로 로그인
    @Override
    public void register(User user) {
        HttpSession session = MyUtil.getSession();
        //예외처리
        if (loggedIn(session))
            throw new RuntimeException("already logged in.");
        if (getSameAccountUser(user) != null)
            throw new RuntimeException("is duplicated account id.");
        if (getSameNicknameUser(user) != null)
            throw new RuntimeException("is duplicated nickname");
        //계정 추가
        user.setPassword(getHashedPw(user));
        userMapper.insertUser(user);
        //세션 생성
        Long userId = getSameAccountUser(user).getId();
        session.setAttribute("user-id", userId);
    }
    //로그인 중이거나 일치하는 계정이 없거나 이미 탈퇴한 회원이거나 비밀번호가 잘못된 경우 예외 발생
    //로그인에 성공하면 세션 생성
    @Override
    public void login(User user) {
        HttpSession session = MyUtil.getSession();
        //예외처리
        if(loggedIn(session) )
            throw new RuntimeException("already logged in.");
        User dbUser = getSameAccountUser(user);
        if(dbUser==null || dbUser.isDeleted() || MyUtil.incorrectPw(user, dbUser) )
            throw new RuntimeException("wrong account id or password.");
        //세션 생성
        session.setAttribute("user-id", dbUser.getId() );
    }
    //로그인 중이 아닐 경우 예외 발생
    //로그인 중일 경우 세션을 지우면서 로그아웃
    @Override
    public void logout() {
        HttpSession session = MyUtil.getSession();
        //예외처리
        if(!loggedIn(session) )
            throw new RuntimeException("not logged in.");
        //로그아웃
        session.invalidate();
    }
    //로그인 중인 사용자의 정보를 수정
    //로그인 중이 아니거나 계정이나 닉네임이 중복될 경우 예외 발생
    @Override
    public void updateUser(User user) {
        Long userId = MyUtil.getUserId();
        //예외처리
        if(userId==null)
            throw new RuntimeException("not logged in.");
        if(isDuplicatedUser(getSameAccountUser(user), userId) )
            throw new RuntimeException("is duplicated account id.");
        if(isDuplicatedUser(getSameNicknameUser(user), userId) )
            throw new RuntimeException("is duplicated nickname.");
        //정보 수정
        user.setId(userId);
        user.setPassword(getHashedPw(user) );
        userMapper.updateUser(user);
    }
    //로그인 중이 아니거나 비밀번호가 틀릴 경우 예외 발생
    //사용자는 soft delete 로 지워지고 회원탈퇴가 끝나면 로그아웃
    @Override
    public void withdrawal(User user) {
        Long userId = MyUtil.getUserId();
        //예외처리
        if(userId==null)
            throw new RuntimeException("not logged in.");
        User dbUser = userMapper.getUserById(userId);
        if(MyUtil.incorrectPw(user, dbUser) )
            throw new RuntimeException("is wrong password.");
        //회원 탈퇴(soft delete)
        userMapper.softDeleteUser(userId);
        logout();
    }

    private boolean loggedIn(HttpSession session){
        return session.getAttribute("user-id") != null;
    }
    private User getSameAccountUser(User user){
        return userMapper.getUserByAccountId(user.getAccount_id() );
    }
    private User getSameNicknameUser(User user){
        return userMapper.getUserByNickName(user.getNick_name() );
    }
    private String getHashedPw(User user){
        return BCrypt.hashpw(user.getPassword(), BCrypt.gensalt() );
    }
    private boolean isDuplicatedUser(User user, Long userId){
        return user != null && MyUtil.isNotSameId(user,userId);
    }
}
