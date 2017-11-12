package com.oktrueque.service;

import com.oktrueque.model.User;

import java.util.List;

public interface NotificationService {

    void sendTruequeAcceptedNotification(List<String> usernames, User userOrigin);
}
