package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sohagmedia.example.demo.Entity.Like;
import sohagmedia.example.demo.Service.LikeService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/save")
    public Like saveLike(@RequestBody Like like) {
        return likeService.saveLike(like);
    }

    @GetMapping("/{id}")
    public Optional<Like> getLikeById(@PathVariable Long id) {
        return likeService.getLikeById(id);
    }

    @GetMapping
    public List<Like> getAllLikes() {
        return likeService.getAllLikes();
    }

    @DeleteMapping("/{id}")
    public void deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(
            @RequestParam String email,
            @RequestParam Long postId) {
        try {
            var result = likeService.toggleLike(email, postId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{postId}")
    public ResponseEntity<String> toggleLikeLegacy(
            @PathVariable Long postId,
            @RequestParam String email) {

        String result = likeService.toggleLike(email, postId).toString();
        return ResponseEntity.ok(result);
    }
}