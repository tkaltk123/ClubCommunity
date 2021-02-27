package repository;

import domain.Board;
import domain.Club;
import domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    //insert
    void insertPost(Post post);
    //select
    List<Post> getPosts(Long boardId);
    Post getPostById(Long postId);
    //update
    void increaseHit(Long postId);
    void updatePost(Post post);
    void softDeletePost(Long postId);
}
