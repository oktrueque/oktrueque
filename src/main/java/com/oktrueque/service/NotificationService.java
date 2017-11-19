package com.oktrueque.service;

import com.oktrueque.model.Notification;
import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;

import java.util.List;

public interface NotificationService {

    void sendTruequeAcceptedNotification(List<String> usernames, User userOrigin);

    void sendTruequeModifiedForItemTrocadoNotification(List<String> usernames, UserLite userOrigin);

    void sendTruequeAcceptedByMeNotification(String username, User userOrigin);

    void sendTruequeProposedNotification(String username, User userOrigin);

    void sendTruequeCanceledByUser(List<String> usernames, User userOrigin);

    void sendTruequeRejectedByUser(List<String> usernames, User userOrigin);

    List<Notification> getNotificationsByUsername(String username);
}
