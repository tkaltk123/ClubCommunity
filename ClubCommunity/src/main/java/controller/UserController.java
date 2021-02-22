package controller;

import domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/join", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "회원 가입", notes = "회원가입을 처리합니다.")
    public ResponseEntity<String> join(
            @ApiParam(value = "(required: account_id, password, nick_name)", required = true) @RequestBody  User user) {
        return userService.register(user)?
                new ResponseEntity<>("회원가입 성공",HttpStatus.OK):
                new ResponseEntity<>("회원가입 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그인", notes = "로그인을 처리합니다.")
    public ResponseEntity<String> login(
            @ApiParam(value = "(required: account_id, password)", required = true) @RequestBody User user) {
        return userService.login(user)?
                new ResponseEntity<>("로그인 성공",HttpStatus.OK):
                new ResponseEntity<>("로그인 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그아웃", notes = "사용자를 로그아웃 시킵니다.")
    public ResponseEntity<String> logout() {
        return userService.logout()?
                new ResponseEntity<>("로그아웃 되었습니다.",HttpStatus.OK):
                new ResponseEntity<>("이미 로그아웃 되어있습니다.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json")
    @ApiOperation(value = "정보 수정", notes = "사용자의 정보를 수정합니다.")
    public ResponseEntity<String> updateUser(
            @ApiParam(value = "(required: account_id, password, nick_name)", required = true) @RequestBody  User user) {
        return userService.updateUser(user)?
                new ResponseEntity<>("사용자 정보를 수정했습니다.",HttpStatus.OK):
                new ResponseEntity<>("잘못된 정보입니다.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.DELETE, consumes = "application/json")
    @ApiOperation(value = "회원 탈퇴", notes = "사용자를 탈퇴시킵니다.")
    public ResponseEntity<String> withdrawal(
            @ApiParam(value = "(required: account_id, password)", required = true) @RequestBody User user
    ) {
        return userService.withdrawal(user)?
                new ResponseEntity<>("회원탈퇴 성공",HttpStatus.OK):
                new ResponseEntity<>("회원탈퇴 실패",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
