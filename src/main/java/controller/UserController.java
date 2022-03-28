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

import javax.validation.Valid;

//실패는 서비스의 예외로 판단하고 무사히 처리됐을 경우 성공으로 판단
//단순 검증인 로그인과 회원 탈퇴를 제외하고 회원가입과 정보 수정은 @Valid 로 유효성을 검사함
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "회원 가입", notes = "회원가입을 처리합니다.")
    public ResponseEntity<String> join(
            @ApiParam(value = "(required: account_id, password, nick_name)", required = true)
            @RequestBody @Valid User user) {
        userService.register(user);
        return new ResponseEntity<>("join success",HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그인", notes = "로그인을 처리합니다.")
    public ResponseEntity<String> login(
            @ApiParam(value = "(required: account_id, password)", required = true)
            @RequestBody User user) {
        userService.login(user);
        return new ResponseEntity<>("login success",HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json")
    @ApiOperation(value = "로그아웃", notes = "사용자를 로그아웃 시킵니다.")
    public ResponseEntity<String> logout() {
        userService.logout();
        return new ResponseEntity<>("logout success",HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json")
    @ApiOperation(value = "정보 수정", notes = "사용자의 정보를 수정합니다.")
    public ResponseEntity<String> updateUser(
            @ApiParam(value = "(required: account_id, password, nick_name)", required = true)
            @RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>("modify success",HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.DELETE, consumes = "application/json")
    @ApiOperation(value = "회원 탈퇴", notes = "사용자를 탈퇴시킵니다.")
    public ResponseEntity<String> withdrawal(
            @ApiParam(value = "(required:password)", required = true)
            @RequestBody User user) {
        userService.withdrawal(user);
        return new ResponseEntity<>("withdrawal success",HttpStatus.OK);
    }
}
