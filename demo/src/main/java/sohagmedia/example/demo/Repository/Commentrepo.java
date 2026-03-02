package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Comment;

@Repository
public interface Commentrepo extends JpaRepository<Comment, Long> {

}