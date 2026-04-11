package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Like;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LikeService {


    Optional<Like> findByUserAndPost(User user, Post post);

    // LikeService.java ফাইলে এটি যোগ করো
    Map<String, Object> toggleLike(String email, Long postId);
    Like saveLike(Like like);
     Optional<Like>findbyUserAndPost(String email, Long postId);

    Optional<Like> getLikeById(Long id);

    List<Like> getAllLikes();

    void deleteLike(Long id);

}