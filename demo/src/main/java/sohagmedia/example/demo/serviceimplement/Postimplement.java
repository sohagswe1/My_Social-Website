package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Repository.PostRepo;
import sohagmedia.example.demo.Service.PostService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Postimplement implements PostService {

    private final PostRepo postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}