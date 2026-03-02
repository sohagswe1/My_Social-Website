package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowService {

    Follow saveFollow(Follow follow);

    Optional<Follow> getFollowById(Long id);

    List<Follow> getAllFollows();

    void deleteFollow(Long id);

}