package service;

import domain.Board;
import domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BoardMapper;
import repository.ClubMapper;
import repository.PostMapper;
import util.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    ClubMapper clubMapper;
    @Autowired
    BoardMapper boardMapper;
    @Autowired
    PostMapper postMapper;
    static final int PAGE_SIZE = 20;
    @Override
    public void createPost(Post post) {
        post.setWriter_id(MyUtil.getUserId() );
        //예외처리
        checkBoard(post);
        //게시글 작성
        postMapper.insertPost(post);
    }
    public Long getPostsCount(Long boardId){
        return postMapper.getPostsCount(boardId);
    }
    @Override
    public List<Post> getPosts(Long boardId, Long page) {
        Post post = new Post();
        post.setBoard_id(boardId);
        post.setWriter_id(MyUtil.getUserId() );
        //예외처리
        checkBoard(post);
        //게시판 조회
        long offset = (page - 1) * PAGE_SIZE;
        if(offset < 0)
            offset = 0;
        return postMapper.getPosts(boardId, offset, PAGE_SIZE);
    }
    @Override
    public Post getPost(Long postId)
    {
        HttpSession session = MyUtil.getSession();
        Post post = postMapper.getPostById(postId);
        //예외처리
        checkBoard(post);
        //조회수 증가
        if(session.getAttribute(postId.toString() ) == null) {
            post.setHit(post.getHit() + 1);
            postMapper.increaseHit(postId);
            session.setAttribute(postId.toString(),true);
        }
        //게시글 조회
        return post;
    }

    @Override
    public void updatePost(Post post) {
        checkPost(post.getId() );
        postMapper.updatePost(post);
    }

    @Override
    public void deletePost(Long postId) {
        checkPost(postId);
        postMapper.softDeletePost(postId);
    }


    private boolean notExistBoard(Post post){
        return boardMapper.getBoardById(post.getBoard_id() ) == null;
    }
    private boolean notJoinedClub(Post post){
        Board board = boardMapper.getBoardById(post.getBoard_id() );
        Long clubId = board.getClub_id();
        if(clubId == null)
            return false;
        Boolean status = clubMapper.getJoinedStatus(post.getWriter_id(), clubId);
        return status==null||status;
    }
    private boolean notWrote(Post post){
        return !post.getWriter_id().equals(MyUtil.getUserId());
    }
    //post 를 받아 게시판이 존재하고 사용자가 접근 가능한지 확인한다.
    private void checkBoard(Post post){
        if(post == null || notExistBoard(post))
            throw new RuntimeException("board doesn't exist.");
        if(notJoinedClub(post) )
            throw new RuntimeException("not joined club.");
    }
    //post id를 받아 게시글이 존재하는지와 사용자가 권한을 가지고 있는지를 확인한다.
    private void checkPost(Long postId){
        Post dbPost = postMapper.getPostById(postId);
        //예외처리
        if(dbPost == null || notExistBoard(dbPost))
            throw new RuntimeException("post doesn't exist.");
        if(notWrote(dbPost) )
            throw new RuntimeException("don't have permission.");
    }
}
