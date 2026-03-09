package sohagmedia.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sohagmedia.example.demo.Entity.Reel;
import sohagmedia.example.demo.Service.ReelsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reels")
@RequiredArgsConstructor
public class Reelscontroller {

    private final ReelsService reelsService;

    @PostMapping
    public Reel saveReel(@RequestBody Reel reel) {
        return reelsService.saveReel(reel);
    }

    @GetMapping("/{id}")
    public Optional<Reel> getReelById(@PathVariable Long id) {
        return reelsService.getReelById(id);
    }

    @GetMapping
    public List<Reel> getAllReels() {
        return reelsService.getAllReels();
    }

    @DeleteMapping("/{id}")
    public void deleteReel(@PathVariable Long id) {
        reelsService.deleteReel(id);
    }
}