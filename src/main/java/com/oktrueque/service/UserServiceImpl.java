package com.oktrueque.service;

import com.oktrueque.model.User;
import com.oktrueque.model.VerificationToken;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class UserServiceImpl  implements UserService{

    @Autowired
    private EmailService emailService;

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    public void updateUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean checkIfUserExists(String email, String username){ return userRepository.checkIfUserExists(email, username)>0;}

    public User getUserByEmailOrUsername(String email, String username){
        return userRepository.findByEmailOrUsername(email, username);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void sendVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepository.save(verificationToken);
        String uriConfirm = "localhost:8080/" + user.getUsername() +
                "/" + token + "/confirm";
        emailService.sendMail(user.getEmail(), "Validar cuenta", uriConfirm);
    }

    public boolean confirmAcount(String username, String token) {
        VerificationToken tokenStored  = verificationTokenRepository.findByToken(token);
        User userStored = userRepository.findOne(tokenStored.getUser().getId());
        if (tokenStored == null || userStored == null || !userStored.getUsername().equals(username)){
            return false;
        }
        userStored.setStatus(1);
        return true;
    }
}
