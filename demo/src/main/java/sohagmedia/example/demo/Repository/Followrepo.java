package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Follow;

@Repository
public interface Followrepo extends JpaRepository<Follow, Long> {
    //follow sohag...........................follow....

}