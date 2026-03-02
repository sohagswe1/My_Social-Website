package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    Like saveLike(Like like);

    Optional<Like> getLikeById(Long id);

    List<Like> getAllLikes();

    void deleteLike(Long id);

}