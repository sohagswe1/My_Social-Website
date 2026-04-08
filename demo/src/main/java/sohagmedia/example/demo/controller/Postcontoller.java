package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Service.PostService;
import sohagmedia.example.demo.Service.Userservice;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class Postcontoller {
@Autowired
    private  PostService postService;
@Autowired
    private  Userservice userservice;

    @PostMapping
    public String savePost(
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("email") String email,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        // কন্ট্রোলারে আর নতুন পোস্ট অবজেক্ট বানানোর দরকার নেই,
        // সব কাজ সরাসরি সার্ভিসের মাধ্যমে করো।
        return postService.savePost(content, email, file);
    }
    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}