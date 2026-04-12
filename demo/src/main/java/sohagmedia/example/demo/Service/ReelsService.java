package sohagmedia.example.demo.Service;

import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Reel;

import java.util.List;
import java.util.Optional;

public interface ReelsService {

    Reel saveReel(Reel reel);

    Optional<Reel> getReelById(Long id);

    List<Reel> getAllReels();

    void deleteReel(Long id);

    // নতুন method - email এবং video দিয়ে reel upload করুন
    Reel uploadReel(String email, MultipartFile video, String caption, MultipartFile thumbnail);

}