package controller;

import domain.Board;
import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.BoardService;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    BoardService boardService;
    @ResponseBody
    @RequestMapping(value = "/boards", method = RequestMethod.GET)
    @ApiOperation(value = "게시판 조회", notes = "사용 가능한 게시판을 불러옵니다.")
    public ResponseEntity<List<Board>> readBoards() {
        return new ResponseEntity<>(boardService.getBoards(), HttpStatus.OK);
    }
}
