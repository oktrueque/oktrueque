package com.oktrueque.service;

import com.oktrueque.model.User;

/**
 * Created by Facundo on 12/07/2017.
 */
public interface UserService {

    User addUser(User user);

    void updateUser(User user);

    Boolean checkIfUserExists(String email, String username);

    User getUserByEmailOrUsername(String email, String username);

    User getUserByUsername(String username);

    void sendVerificationToken(User user);

    boolean confirmAccount(String username, String token);
}
