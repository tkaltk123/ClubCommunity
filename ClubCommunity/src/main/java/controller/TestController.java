package controller;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/test")
    public String test(HttpSession session){
        Object loginStatus = session.getAttribute(("login"));
        if(loginStatus!=null&&(boolean) loginStatus)
            return "login true";
        return "login false";
    }
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(){
        return "main";
    }
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(){
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> signup(@RequestBody User user) {
        return userService.register(user) ? new ResponseEntity<>("success", HttpStatus.OK) :
                new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
        if(userService.login(user)){
            session.setAttribute("login",true);
            session.setAttribute("account_id",user.getAccount_id());
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
