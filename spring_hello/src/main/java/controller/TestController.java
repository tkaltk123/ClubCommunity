package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String helloTest1(){
        System.out.println("hello1");
        return "hello";
    }
    @ResponseBody
    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public String helloTest2(){
        System.out.println("hello2");
        return "hello";
    }
}
