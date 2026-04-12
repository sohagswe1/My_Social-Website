package sohagmedia.example.demo.serviceimplement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Comment;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.Commentrepo;
import sohagmedia.example.demo.Repository.PostRepo;
import sohagmedia.example.demo.Repository.UserRepo;
import sohagmedia.example.demo.Service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class commentimplement implements CommentService {
    @Autowired
    private Commentrepo commentRepository;
        @Autowired
        private UserRepo userRepository;
        @Autowired
        private PostRepo postRepository;

    @Override
    @Transactional
    public Comment saveComment(Comment comment) {
        // ১. ফ্রন্টএন্ড থেকে আসা ইমেইল দিয়ে ডাটাবেস থেকে আসল User খুঁজে বের করা
        User user = userRepository.findByEmail(comment.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found with email: " + comment.getUser().getEmail()));


        Post post = postRepository.findById(comment.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found with ID: " + comment.getPost().getId()));


        comment.setUser(user);
        comment.setPost(post);


        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}