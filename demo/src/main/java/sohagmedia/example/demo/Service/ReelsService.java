package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Reel;

import java.util.List;
import java.util.Optional;

public interface ReelsService {

    Reel saveReel(Reel reel);

    Optional<Reel> getReelById(Long id);

    List<Reel> getAllReels();

    void deleteReel(Long id);

}