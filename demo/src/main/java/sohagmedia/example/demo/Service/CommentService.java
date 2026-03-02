package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment saveComment(Comment comment);

    Optional<Comment> getCommentById(Long id);

    List<Comment> getAllComments();

    void deleteComment(Long id);

}