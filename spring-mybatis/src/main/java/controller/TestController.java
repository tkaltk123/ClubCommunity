package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.ResponseBody;
import repository.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.UserService;

@Controller
public class TestController {
    @Autowired
    TestMapper testMapper;
    @Autowired
    UserService userService;
    @ResponseBody
    @RequestMapping(value = "/test1")
    public String test1(){
        String s = testMapper.getTime();
        System.out.println("test1");
        System.out.println(testMapper.getTime());
        return "test";
    }
    @ResponseBody
    @RequestMapping(value = "/test2")
    public String test2() throws JsonProcessingException {
        System.out.println("test2");
        System.out.println(new ObjectMapper().writeValueAsString(userService.getUsers()));
        return "test";
    }
}
