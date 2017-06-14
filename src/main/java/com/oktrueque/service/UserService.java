package com.oktrueque.service;

import com.oktrueque.model.User;

public interface UserService {

    User getUserById(Long id);

    void addUser(User user);

    void updateUser(User user);

//    User getUserByEmail(String email);
//
//    User getUserByUserName(String username);

   User getUserByEmailOrUsername(String email, String username);

}
