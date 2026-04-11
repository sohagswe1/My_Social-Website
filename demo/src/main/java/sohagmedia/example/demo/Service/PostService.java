package sohagmedia.example.demo.Service;

import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post  savePost(String  content,String email, MultipartFile file);

    Optional<Post> getPostById(Long id);

    List<Post> getAllPosts();

    void deletePost(Long id);

}