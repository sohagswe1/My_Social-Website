package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post savePost(Post post);

    Optional<Post> getPostById(Long id);

    List<Post> getAllPosts();

    void deletePost(Long id);

}