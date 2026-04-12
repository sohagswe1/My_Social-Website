package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sohagmedia.example.demo.Entity.Comment;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Service.CommentService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class Commnetcontroller {


    private final CommentService commentService;



    @PostMapping("/save-by-email")
    public Comment saveCommentByEmail(
            @RequestParam(value = "postId") Long postId,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "content") String content) {
        Comment comment = new Comment();
        comment.setContent(content);

        User user = new User();
        user.setEmail(email);
        comment.setUser(user);

        Post post = new Post();
        post.setId(postId);
        comment.setPost(post);

        return commentService.saveComment(comment);
    }

    @GetMapping("/{id}")
    public Optional<Comment> getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}