package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Comment;
import sohagmedia.example.demo.Repository.Commentrepo;
import sohagmedia.example.demo.Service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class commentimplement implements CommentService {

    private final Commentrepo commentRepository;

    @Override
    public Comment saveComment(Comment comment) {
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