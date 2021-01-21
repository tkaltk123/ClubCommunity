package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.NullRefService;

@Controller
public class ThrowController {
    @Autowired
    NullRefService nullRefService;
    @RequestMapping(value = "throw",method = RequestMethod.GET)
    public ResponseEntity throwTest(){
        try {
            nullRefService.reference();
        }
        catch (Exception e){
            System.out.println("예외 발생");
        }
        return new ResponseEntity("nullpointException", HttpStatus.OK);
    }
}
