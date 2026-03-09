package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sohagmedia.example.demo.Entity.Follow;
import sohagmedia.example.demo.Service.FollowService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class Followcontroller {

    private final FollowService followService;

    @PostMapping
    public Follow saveFollow(@RequestBody Follow follow) {
        return followService.saveFollow(follow);
    }

    @GetMapping("/{id}")
    public Optional<Follow> getFollowById(@PathVariable Long id) {
        return followService.getFollowById(id);
    }

    @GetMapping
    public List<Follow> getAllFollows() {
        return followService.getAllFollows();
    }

    @DeleteMapping("/{id}")
    public void deleteFollow(@PathVariable Long id) {
        followService.deleteFollow(id);
    }
}