package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;

import java.util.List;

public interface NotificationService {

    void sendTruequeAcceptedNotification(List<String> usernames, User userOrigin);

    void sendTruequeModifiedForItemTrocadoNotification(List<String> usernames, UserLite userOrigin);
}
