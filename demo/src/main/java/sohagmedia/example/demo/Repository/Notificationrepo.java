package sohagmedia.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sohagmedia.example.demo.Entity.Notification;

@Repository
public interface Notificationrepo extends JpaRepository<Notification, Long> {

}