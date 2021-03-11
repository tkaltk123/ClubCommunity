package service;

import domain.Board;
import domain.Comment;

import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    List<Comment> getComments(Long postId, Long page);
    void updateComment(Comment comment);
    void deleteComment(Long commentId);
}
