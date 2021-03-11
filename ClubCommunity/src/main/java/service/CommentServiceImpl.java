package service;

import domain.Board;
import domain.Comment;
import domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BoardMapper;
import repository.ClubMapper;
import repository.CommentMapper;
import repository.PostMapper;
import util.MyUtil;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    ClubMapper clubMapper;
    @Autowired
    BoardMapper boardMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentMapper commentMapper;
    static final int PAGE_SIZE = 50;

    @Override
    public void createComment(Comment comment) {
        Post dbPost = postMapper.getPostById(comment.getPost_id() );
        checkPost(dbPost);
        comment.setWriter_id(MyUtil.getUserId());
        commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> getComments(Long postId, Long page) {
        Post dbPost = postMapper.getPostById(postId);
        checkPost(dbPost);
        long offset = (page - 1) * PAGE_SIZE;
        if(offset < 0)
            offset = 0;
        List<Comment> result = commentMapper.getComments(postId, offset, PAGE_SIZE);
        if(result.size() == 0)
            throw new RuntimeException("page out of range.");
        return result;
    }

    @Override
    public void updateComment(Comment comment) {
        checkComment(comment.getId());
        commentMapper.updateComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        checkComment(commentId);
        commentMapper.softDeleteComment(commentId);

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
    //post id를 받아 게시글이 존재하는지와 사용자가 게시글에 접근 가능한지를 확인한다.
    private void checkPost(Post dbPost){
        if(dbPost == null || notExistBoard(dbPost) )
            throw new RuntimeException("post doesn't exist.");
        dbPost.setWriter_id(MyUtil.getUserId() );
        if(notJoinedClub(dbPost) )
            throw new RuntimeException("not joined club.");
    }
    //comment id를 받아 댓글이 존재하는지와 사용자가 권한을 가지고 있는지를 확인한다.
    private void checkComment(Long commentId){
        Comment dbComment = commentMapper.getCommentById(commentId);
        Post dbPost = null;
        if(dbComment != null)
            dbPost = postMapper.getPostById(dbComment.getPost_id() );
        //예외처리
        if(dbComment == null || dbPost == null || notExistBoard(dbPost) )
            throw new RuntimeException("comment doesn't exist.");
        if(!dbComment.getWriter_id().equals(MyUtil.getUserId() ) )
            throw new RuntimeException("don't have permission.");

    }
}
