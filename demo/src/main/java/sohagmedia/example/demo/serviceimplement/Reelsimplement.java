package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Reel;
import sohagmedia.example.demo.Repository.Reelsrepo;
import sohagmedia.example.demo.Service.ReelsService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Reelsimplement implements ReelsService {

    private final Reelsrepo reelRepository;

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
}