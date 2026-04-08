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
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Service.Userservice;

import java.util.List;
import java.util.Optional;
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
    public String login(@RequestBody User user) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword()
                        )
                );

        if(authentication.isAuthenticated()) {


            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "Login Successful";
        }

        return "Login Failed";
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userservice.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); // এটি রিঅ্যাক্টকে সাকসেস কোড দিবে
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userservice.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userservice.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userservice.deleteUser(id);
    }

    @GetMapping("/email/{email}")
    User getUserByEmail(@RequestParam String email) {
        return  userservice.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}