package service;

import domain.Board;
import domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BoardMapper;
import util.MyUtil;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    BoardMapper boardMapper;

    @Override
    public void createPost(Post post) {

    }

    @Override
    public List<Post> getPosts(Long boardId) {
        return null;
    }

    @Override
    public Post getPost(Long postId) {
        return null;
    }

    @Override
    public void updatePost(Post post) {

    }

    @Override
    public void deletePost(Long postId) {

    }
}
