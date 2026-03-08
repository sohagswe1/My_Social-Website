package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Follow;
import sohagmedia.example.demo.Repository.Followrepo;
import sohagmedia.example.demo.Repository.Followrepo;
import sohagmedia.example.demo.Service.FollowService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class followimplement implements FollowService {

    private final Followrepo followRepository;

    @Override
    public Follow saveFollow(Follow follow) {
        return followRepository.save(follow);
    }

    @Override
    public Optional<Follow> getFollowById(Long id) {
        return followRepository.findById(id);
    }

    @Override
    public List<Follow> getAllFollows() {
        return followRepository.findAll();
    }

    @Override
    public void deleteFollow(Long id) {
        followRepository.deleteById(id);
    }
}