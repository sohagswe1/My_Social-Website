package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Autowired    private  Userservice userservice;

    @PostMapping("/save")
    public ResponseEntity<?> savePost(
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("email") String email,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        try {
            // সার্ভিস কল করে পোস্ট সেভ করো
            Post savedPost = postService.savePost(content, email, file);
            return ResponseEntity.ok(savedPost);
        } catch (Exception e) {
            // কনসোলে এরর ডিটেইলস প্রিন্ট করবে
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving post: " + e.getMessage());
        }
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