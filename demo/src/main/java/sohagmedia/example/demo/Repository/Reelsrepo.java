package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Reel;

@Repository
public interface Reelsrepo extends JpaRepository<Reel, Long> {

}