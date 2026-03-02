package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

}