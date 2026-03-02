package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import sohagmedia.example.demo.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
