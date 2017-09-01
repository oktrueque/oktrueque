package com.oktrueque.service;

import com.oktrueque.model.User;

import java.util.List;

/**
 * Created by Facundo on 8/29/2017.
 */
public interface ChatService {

    List<User> getChatsRegistered();

    void loginIntoXmppService(User user);

    void createNewUser(User user);

    void createNewsRosters(List<User> users, String name);

    void logout();
}
