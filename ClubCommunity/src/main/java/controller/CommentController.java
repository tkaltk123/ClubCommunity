package controller;

import domain.Comment;
import domain.Post;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/comments")
public class CommentController {
    @Autowired
    CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "댓글 작성", notes = "선택한 게시글에 댓글을 작성합니다.")
    public ResponseEntity<String> createComment(
            @ApiParam(value = "(required: post_id, content)", required = true) @RequestBody @Valid Comment comment) {
        commentService.createComment(comment);
        return new ResponseEntity<>("create comment success", HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/posts/{post-id}", method = RequestMethod.GET)
    @ApiOperation(value = "댓글 조회", notes = "선택한 게시글의 댓글들을 보여줍니다.")
    public ResponseEntity<List<Comment>> readComments(
            @PathVariable("post-id") Long postId){
        return new ResponseEntity<>(commentService.getComments(postId), HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{comment-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "댓글 수정", notes = "선택한 댓글을 수정합니다.")
    public ResponseEntity<String>  updateComment(
            @PathVariable("comment-id") Long commentId,
            @ApiParam(value = "(required : content)", required = true)
            @RequestBody @Valid Comment comment) {
        comment.setId(commentId);
        commentService.updateComment(comment);
        return new ResponseEntity<>("update comment success", HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{comment-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "댓글 삭제", notes = "선택한 댓글을 삭제합니다.")
    public ResponseEntity<String>  deleteComment(
            @PathVariable("comment-id") Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("delete comment success", HttpStatus.OK);
    }
}
