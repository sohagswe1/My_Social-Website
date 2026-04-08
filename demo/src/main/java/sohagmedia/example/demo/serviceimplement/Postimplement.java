package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.PostRepo;
import sohagmedia.example.demo.Service.PostService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Postimplement implements PostService {
@Autowired
    private  PostRepo postRepository;
@Autowired
    private Userserviceimplement userservice;


    @Override
    public String savePost(String content, String email, MultipartFile file) {
        Post post1 = new Post();
        post1.setContent(content); // টেক্সট কন্টেন্ট সেট করা হলো

        if (file != null && !file.isEmpty()) {
            post1.setImageUrl(file.getOriginalFilename());
        }

        User user = userservice.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("ইউজার পাওয়া যায়নি!"));

        post1.setUser(user);
        postRepository.save(post1);

        return "post saved successfully";
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