package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Like;
import sohagmedia.example.demo.Repository.Likerepo;
import sohagmedia.example.demo.Repository.Likerepo;
import sohagmedia.example.demo.Service.LikeService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Likeimplement implements LikeService {
@Autowired
    private final Likerepo likeRepository;

    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Optional<Like> getLikeById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public List<Like> getAllLikes() {
        return likeRepository.findAll();
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }
}