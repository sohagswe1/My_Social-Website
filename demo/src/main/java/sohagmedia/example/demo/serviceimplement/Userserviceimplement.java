package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.User;
import sohagmedia.example.demo.Repository.UserRepo;
import sohagmedia.example.demo.Repository.UserRepo;
import sohagmedia.example.demo.Service.Userservice;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Userserviceimplement implements Userservice {

    private final UserRepo userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}