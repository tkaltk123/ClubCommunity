package service;

import domain.Board;
import domain.Post;

import java.util.List;

public interface PostService {
    void createPost(Post post);
    List<Post> getPosts(Long boardId, Long page);
    Long getPostsCount(Long boardId);
    Post getPost(Long postId);
    void updatePost(Post post);
    void deletePost(Long postId);
}
