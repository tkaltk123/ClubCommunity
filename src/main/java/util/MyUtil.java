package util;

import domain.Club;
import domain.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class MyUtil {
    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }
    public static Long getUserId(){
        return (Long)MyUtil.getSession().getAttribute("user-id");
    }
    public static boolean incorrectPw(User user, User dbUser){
        return !BCrypt.checkpw(user.getPassword(), dbUser.getPassword() );
    }
    public static boolean isNotSameId(User user, Long userId){
        return !user.getId().equals(userId);
    }
    public static boolean isNotSameId(Club club, Long clubId){
        return !club.getId().equals(clubId);
    }
}
