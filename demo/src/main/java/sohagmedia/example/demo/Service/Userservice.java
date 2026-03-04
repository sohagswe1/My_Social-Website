package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.User;

import java.util.List;
import java.util.Optional;

public interface Userservice {

    User saveUser(User user);

    Optional<User> getUserById(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);


}