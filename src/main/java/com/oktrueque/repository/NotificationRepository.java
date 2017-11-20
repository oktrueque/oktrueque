package com.oktrueque.repository;

import com.oktrueque.model.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByUsernameOrderByDateDesc(String username);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Notification n set n.checked=true where n.username =:username")
    void clearNotifications(@Param("username") String username);
}
