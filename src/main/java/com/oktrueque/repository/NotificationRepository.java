package com.oktrueque.repository;

import com.oktrueque.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByUsernameOrderByDateDesc(String username);
}
