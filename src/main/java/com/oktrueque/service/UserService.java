package com.oktrueque.service;

import com.oktrueque.model.User;
import org.springframework.http.ResponseEntity;

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

    User getUserById(long id);

    boolean confirmAccount(String username, String token);

    ResponseEntity resetPassword(String datos);

}
