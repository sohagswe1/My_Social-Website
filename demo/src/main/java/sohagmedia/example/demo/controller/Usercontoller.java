package sohagmedia.example.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Service.Userservice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class Usercontoller {

    private final Userservice userservice;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
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
            return "Login Successful";
        }

        return "Login Failed";
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userservice.saveUser(user);
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
}