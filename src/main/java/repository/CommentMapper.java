package repository;

import domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    //insert
    void insertComment(Comment comment);
    //select
    List<Comment> getComments(Long postId, Long offset, int count);
    Comment getCommentById(Long commentId);
    //update
    void updateComment(Comment comment);
    void softDeleteComment(Long commentId);
}
