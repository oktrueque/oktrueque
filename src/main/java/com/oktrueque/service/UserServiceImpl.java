package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);

    }

    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

//
//    public User getUserByEmailOrUsername(String email){
//        return userRepository.findAllByEmailOrUsername(email);
//    }
}
