package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Reel;
import sohagmedia.example.demo.Service.ReelsService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/reels")
@RequiredArgsConstructor
public class Reelscontroller {
    @Autowired
    private ReelsService reelsService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadReel(
            @RequestParam String email,
            @RequestParam(value = "caption", required = false) String caption,
            @RequestParam(value = "video") MultipartFile video,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail) {
        try {
            // Validation
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (video == null || video.isEmpty()) {
                return ResponseEntity.badRequest().body("Video file is required");
            }

            // Video upload করুন
            Reel uploadedReel = reelsService.uploadReel(email, video, caption, thumbnail);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedReel);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error uploading reel: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> saveReel(@RequestBody Reel reel) {
        try {
            Reel savedReel = reelsService.saveReel(reel);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error saving reel: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReelById(@PathVariable Long id) {
        try {
            Optional<Reel> reel = reelsService.getReelById(id);
            if (reel.isPresent()) {
                return ResponseEntity.ok(reel.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reel not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllReels() {
        try {
            List<Reel> reels = reelsService.getAllReels();
            return ResponseEntity.ok(reels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReel(@PathVariable Long id) {
        try {
            Optional<Reel> reel = reelsService.getReelById(id);
            if (!reel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reel not found");
            }
            reelsService.deleteReel(id);
            return ResponseEntity.ok("Reel deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
}