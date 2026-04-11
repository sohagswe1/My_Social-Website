package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Like;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;

import java.util.Optional;

@Repository
public interface Likerepo extends JpaRepository<Like, Long> {
    long  countByPostId(Long postId);
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserEmailAndPostId(String email, Long postId);

}