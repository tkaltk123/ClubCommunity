package controller;

import domain.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/posts")
public class PostController {

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 작성", notes = "선택한 게시판에 게시글을 작성합니다.")
    public ResponseEntity<String> createPost(@PathVariable("board-id") Long boardid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/boards/{board-id}", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 조회", notes = "선택한 게시판의 게시글들을 보여줍니다.")
    public String readPosts(@PathVariable("board-id") Long boardid){
        return "club/list";
    }

    @RequestMapping(value = "/{post-id}", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 읽기", notes = "선택한 게시글을 보여줍니다.")
    public String readPost(@PathVariable("board-id") Long boardid, @PathVariable("post-id") Long postid){
        return "club/list";
    }

    @RequestMapping(value = "/{post-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "게시글 수정", notes = "선택한 게시글을 수정합니다.")
    public String updatePost(@PathVariable("board-id") Long boardid, @PathVariable("post-id") Long postid){
        return "club/list";
    }
    @RequestMapping(value = "/{post-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "게시글 삭제", notes = "선택한 게시글을 삭제합니다.")
    public String deletePost(@PathVariable("board-id") Long boardid, @PathVariable("post-id") Long postid){
        return "club/list";
    }


}
