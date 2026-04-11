package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.Post;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.PostRepo;
import sohagmedia.example.demo.Service.PostService;

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
public class Postimplement implements PostService {
@Autowired
    private  PostRepo postRepository;
@Autowired
    private Userserviceimplement userservice;


    @Override
    public Post savePost(String content, String email, MultipartFile file) {
        Post post1 = new Post();
        post1.setContent(content);

        // ১. ছবি হ্যান্ডেল করার অংশ
        if (file != null && !file.isEmpty()) {
            try {
                // তোমার তৈরি করা ফোল্ডারের নাম
                String uploadDir = "upload/";
                Path uploadPath = Paths.get(uploadDir);

                // ফোল্ডার না থাকলে তৈরি করে নেবে
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // একই নামের ছবি থাকলে ঝামেলা হয়, তাই সময়ের সাথে নামটা ইউনিক করা ভালো
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // ফাইলটি পিসির ফোল্ডারে সেভ করা
                try (InputStream inputStream = file.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                }

                // ২. ডাটাবেজে এই ইউনিক নামটা সেট করা
                post1.setImageUrl(fileName);

            } catch (IOException e) {
                throw new RuntimeException("ছবি সেভ করতে সমস্যা হয়েছে: " + e.getMessage());
            }
        }

        // ৩. ইউজার সেট করা এবং সেভ করা
        User user = userservice.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("ইউজার পাওয়া যায়নি!"));

        post1.setUser(user);
        return postRepository.save(post1);


    }
    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}