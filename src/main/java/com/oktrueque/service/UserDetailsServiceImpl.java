package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUser(1, username, username);
        if (user == null) {
            UsernameNotFoundException exception = new UsernameNotFoundException(username);
            LOGGER.info("Login Fail", exception);
            throw exception;
        }
        return user;
    }
}
