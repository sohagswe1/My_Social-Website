package sohagmedia.example.demo.serviceimplement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Like;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.Likerepo;
import sohagmedia.example.demo.Repository.PostRepo;
import sohagmedia.example.demo.Repository.UserRepo;
import sohagmedia.example.demo.Service.LikeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Likeimplement implements LikeService {
@Autowired
    private  Likerepo likeRepository;
    @Autowired
    private  UserRepo userRepository;
    @Autowired
    private PostRepo postRepository;


    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public Optional<Like> findbyUserAndPost(String email, Long postId) {
        return Optional.empty();
    }

    @Override
    public Optional<Like> findByUserAndPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user, post);
    }

    @Override
    @Transactional
    public Map<String, Object> toggleLike(String email, Long postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // চেক করা ইউজার আগে লাইক দিয়েছে কি না
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);

        Map<String, Object> response = new HashMap<>();

        if (existingLike.isPresent()) {
            // লাইক থাকলে রিমুভ করো (Unlike)
            likeRepository.delete(existingLike.get());
            response.put("isLiked", false);
        } else {
            // লাইক না থাকলে নতুন লাইক সেভ করো (Like)
            Like newLike = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(newLike);
            response.put("isLiked", true);
        }

        // বর্তমান মোট লাইক সংখ্যা বের করা
        long totalLikes = likeRepository.countByPostId(post.getId());
        response.put("currentLikes", totalLikes);

        return response;
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