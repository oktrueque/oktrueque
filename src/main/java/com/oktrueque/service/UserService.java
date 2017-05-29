package com.oktrueque.service;

import com.oktrueque.model.User;

/**
 * Created by Felipe on 7/5/2017.
 */
public interface UserService {

    User getUserById(Long id);

    void addUser(User user);

    //Editar un usuario
    void updateUser(User user);





}
