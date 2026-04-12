package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Reel;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.Reelsrepo;
import sohagmedia.example.demo.Repository.UserRepo;
import sohagmedia.example.demo.Service.ReelsService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Reelsimplement implements ReelsService {
    @Autowired
    private final Reelsrepo reelRepository;
    
    @Autowired
    private UserRepo userRepository;

    @Override
    public Reel saveReel(Reel reel) {
        return reelRepository.save(reel);
    }

    @Override
    public Optional<Reel> getReelById(Long id) {
        return reelRepository.findById(id);
    }

    @Override
    public List<Reel> getAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public void deleteReel(Long id) {
        reelRepository.deleteById(id);
    }

    @Override
    public Reel uploadReel(String email, MultipartFile video, String caption, MultipartFile thumbnail) {
        // ১. User খুঁজে বের করুন
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // ২. Reel object তৈরি করুন
        Reel reel = new Reel();
        reel.setCaption(caption);
        reel.setUser(user);
        reel.setViewCount(0L);
        reel.setLikeCount(0L);

        // ৩. Video upload করুন
        if (video != null && !video.isEmpty()) {
            try {
                String videoFileName = System.currentTimeMillis() + "_video_" + video.getOriginalFilename();
                String uploadDir = "upload/reels";
                Path uploadPath = Paths.get(uploadDir);

                // Directory create করুন যদি না থাকে
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Video file save করুন
                try (InputStream inputStream = video.getInputStream()) {
                    Path videoPath = uploadPath.resolve(videoFileName);
                    Files.copy(inputStream, videoPath, StandardCopyOption.REPLACE_EXISTING);
                }

                reel.setVideoUrl(videoFileName);
            } catch (IOException e) {
                throw new RuntimeException("ভিডিও আপলোড করতে ব্যর্থ: " + e.getMessage());
            }
        }

        // ৪. Thumbnail upload করুন (optional)
        if (thumbnail != null && !thumbnail.isEmpty()) {
            try {
                String thumbnailFileName = System.currentTimeMillis() + "_thumb_" + thumbnail.getOriginalFilename();
                String uploadDir = "upload/reels";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = thumbnail.getInputStream()) {
                    Path thumbPath = uploadPath.resolve(thumbnailFileName);
                    Files.copy(inputStream, thumbPath, StandardCopyOption.REPLACE_EXISTING);
                }

                reel.setThumbnailUrl(thumbnailFileName);
            } catch (IOException e) {
                throw new RuntimeException("থাম্বনেইল আপলোড করতে ব্যর্থ: " + e.getMessage());
            }
        }

        // ৫. Reel save করুন
        return reelRepository.save(reel);
    }
}