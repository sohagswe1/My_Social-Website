package sohagmedia.example.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Entity.UserProfileDTO;
import sohagmedia.example.demo.Service.Userservice;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class Usercontoller {
     @Autowired
    private  Userservice userservice;
        @Autowired
    private  AuthenticationManager authenticationManager;
        @Autowired
    private  PasswordEncoder passwordEncoder;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    user.getEmail(),
                                    user.getPassword()
                            )
                    );

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User authenticatedUser = userservice.findByEmail(user.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                UserProfileDTO profile = UserProfileDTO.fromUser(authenticatedUser);
                return ResponseEntity.ok(profile);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login Failed: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // Validate input
            if (name == null || name.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required");
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

            // File handle করুন
            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    String uploadDir = System.getProperty("user.dir") + "/upload";
                    File directory = new File(uploadDir);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String filePath = uploadDir + File.separator + fileName;
                    file.transferTo(new File(filePath));
                    user.setProfilePicture(fileName);
                } catch (IOException e) {
                    return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
                }
            }

            User savedUser = userservice.saveUser(user);
            UserProfileDTO profile = UserProfileDTO.fromUser(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration Failed: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userservice.getUserById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userservice.getAllUsers();
            // Password ছাড়াই user list convert করুন
            List<UserProfileDTO> userProfiles = users.stream()
                    .map(UserProfileDTO::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userProfiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error fetching users: " + e.getMessage());
        }
    }

    @GetMapping("/profiles/all")
    public ResponseEntity<?> getAllUserProfiles() {
        try {
            List<User> users = userservice.getAllUsers();
            List<UserProfileDTO> userProfiles = users.stream()
                    .map(UserProfileDTO::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userProfiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        try {
            Optional<User> user = userservice.getUserById(id);
            if (user.isPresent()) {
                UserProfileDTO profile = UserProfileDTO.fromUser(user.get());
                return ResponseEntity.ok(profile);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}/profile")
    public ResponseEntity<?> getUserProfileByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userservice.findByEmail(email);
            if (user.isPresent()) {
                UserProfileDTO profile = UserProfileDTO.fromUser(user.get());
                return ResponseEntity.ok(profile);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(@RequestParam String name) {
        try {
            List<User> users = userservice.getAllUsers();
            List<UserProfileDTO> results = users.stream()
                    .filter(u -> u.getName().toLowerCase().contains(name.toLowerCase()))
                    .map(UserProfileDTO::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> user = userservice.getUserById(id);
            if (!user.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            userservice.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            Optional<User> user = userservice.findByEmail(email);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfileDTO profileDTO) {
        try {
            Optional<User> userOptional = userservice.getUserById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            
            User user = userOptional.get();
            if (profileDTO.getName() != null) {
                user.setName(profileDTO.getName());
            }
            if (profileDTO.getBio() != null) {
                user.setBio(profileDTO.getBio());
            }
            
            User updatedUser = userservice.saveUser(user);
            UserProfileDTO result = UserProfileDTO.fromUser(updatedUser);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating profile: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/add-bio")
    public ResponseEntity<?> addBio(
            @PathVariable Long id,
            @RequestParam String bio) {
        try {
            if (bio == null || bio.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Bio cannot be empty");
            }

            Optional<User> userOptional = userservice.getUserById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOptional.get();
            user.setBio(bio);
            
            User updatedUser = userservice.saveUser(user);
            UserProfileDTO result = UserProfileDTO.fromUser(updatedUser);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error adding bio: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/bio")
    public ResponseEntity<?> updateBio(
            @PathVariable Long id,
            @RequestParam String bio) {
        try {
            if (bio == null || bio.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Bio cannot be empty");
            }

            Optional<User> userOptional = userservice.getUserById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOptional.get();
            user.setBio(bio);
            
            User updatedUser = userservice.saveUser(user);
            UserProfileDTO result = UserProfileDTO.fromUser(updatedUser);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error updating bio: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/bio")
    public ResponseEntity<?> deleteBio(@PathVariable Long id) {
        try {
            Optional<User> userOptional = userservice.getUserById(id);
            if (!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            User user = userOptional.get();
            user.setBio(null);
            
            User updatedUser = userservice.saveUser(user);
            UserProfileDTO result = UserProfileDTO.fromUser(updatedUser);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error deleting bio: " + e.getMessage());
        }
    }
}