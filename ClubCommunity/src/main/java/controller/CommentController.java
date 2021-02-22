package controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 작성", notes = "선택한 게시글에 댓글을 작성합니다.")
    public ResponseEntity<String> createComment(@PathVariable("post-id") Long postid) {
        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @RequestMapping(value = "/posts/{post-id}", method = RequestMethod.GET)
    @ApiOperation(value = "댓글 조회", notes = "선택한 게시글의 댓글들을 보여줍니다.")
    public String readComments(@PathVariable("post-id") Long postid){
        return "club/list";
    }

    @RequestMapping(value = "/{comment-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "댓글 수정", notes = "선택한 댓글을 수정합니다.")
    public String updateComment(@PathVariable("comment-id") Long commentid){
        return "club/list";
    }
    @RequestMapping(value = "/{comment-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "댓글 삭제", notes = "선택한 댓글을 삭제합니다.")
    public String deleteComment(@PathVariable("comment-id") Long commentid){
        return "club/list";
    }
}
