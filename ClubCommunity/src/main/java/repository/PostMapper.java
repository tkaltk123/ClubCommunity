package repository;

import domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    //insert
    void insertPost(Post post);
    //select
    Long getPostsCount(Long boardId);
    List<Post> getPosts(Long boardId, Long offset, int size);
    Post getPostById(Long postId);
    //update
    void increaseHit(Long postId);
    void updatePost(Post post);
    void softDeletePost(Long postId);
}
