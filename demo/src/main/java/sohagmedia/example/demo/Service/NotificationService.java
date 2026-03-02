package sohagmedia.example.demo.Service;

import sohagmedia.example.demo.Entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    Optional<Notification> getNotificationById(Long id);

    List<Notification> getAllNotifications();

    void deleteNotification(Long id);

}