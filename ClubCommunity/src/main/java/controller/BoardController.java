package controller;

import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {

    @ResponseBody
    @RequestMapping(value = "/boards", method = RequestMethod.POST)
    @ApiOperation(value = "게시판 조회", notes = "사용 가능한 게시판을 불러옵니다.")
    public ResponseEntity<String> readBoards() {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
