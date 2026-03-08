package sohagmedia.example.demo.serviceimplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sohagmedia.example.demo.Entity.Notification;
import sohagmedia.example.demo.Repository.Notificationrepo;
import sohagmedia.example.demo.Repository.Notificationrepo;
import sohagmedia.example.demo.Service.NotificationService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Notif implements NotificationService {

    private final Notificationrepo notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}