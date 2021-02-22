package controller;

import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/join", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "회원 가입", notes = "사용자가 보낸 정보가 유효할 경우 회원가입을 처리합니다.")
    public ResponseEntity<String> join(@RequestBody User user) {
        return userService.register(user) ? new ResponseEntity<>("success", HttpStatus.OK) :
                new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그인", notes = "사용자의 정보가 일치할 경우 로그인을 처리합니다.")
    public ResponseEntity<String> login(@RequestBody User user) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그아웃", notes = "사용자를 로그아웃 시킵니다.")
    public ResponseEntity<String> logout(@RequestBody User user) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/mypage/modify", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "정보 수정", notes = "사용자의 정보를 수정합니다.")
    public ResponseEntity<String> modify(@RequestBody User user) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/mypage/withdrawal", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "회원 탈퇴", notes = "사용자를 탈퇴시킵니다.")
    public ResponseEntity<String> withdrawal(@RequestBody User user) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
