package controller;

import domain.Club;
import domain.Post;
import domain.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.PostService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/posts")
public class PostController {
    @Autowired
    PostService postService;
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "게시글 작성", notes = "선택한 게시판에 게시글을 작성합니다.")
    public ResponseEntity<String> createPost(
            @ApiParam(value = "(required: board_id, title, content)", required = true)
            @RequestBody @Valid Post post) {
        if(post.getBoard_id() == null)
            new ResponseEntity<>("board_id : not null.", HttpStatus.BAD_REQUEST);
        postService.createPost(post);
        return new ResponseEntity<>("create post success.", HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/boards/{board-id}", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 조회", notes = "선택한 게시판의 게시글들을 보여줍니다.")
    public ResponseEntity<List<Post>> readPosts(@PathVariable("board-id") Long boardId)
    {
        return new ResponseEntity<>(postService.getPosts(boardId), HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{post-id}", method = RequestMethod.GET)
    @ApiOperation(value = "게시글 읽기", notes = "선택한 게시글을 보여줍니다.")
    public ResponseEntity<Post> readPost(
            @PathVariable("post-id") Long postId){
        return new ResponseEntity<>(postService.getPost(postId), HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/{post-id}", method = RequestMethod.PUT)
    @ApiOperation(value = "게시글 수정", notes = "선택한 게시글을 수정합니다.")
    public ResponseEntity<String> updatePost(
            @PathVariable("post-id") Long postId,
            @ApiParam(value = "(required: title, content)", required = true)
            @RequestBody @Valid Post post){
        post.setId(postId);
        postService.updatePost(post);
        return new ResponseEntity<>("post update success.", HttpStatus.OK);
    }
    @RequestMapping(value = "/{post-id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "게시글 삭제", notes = "선택한 게시글을 삭제합니다.")
    public ResponseEntity<String> deletePost(@PathVariable("post-id") Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("post delete success.", HttpStatus.OK);
    }
}
