package com.oktrueque.service;

import com.oktrueque.model.Email;
import com.oktrueque.model.User;
import com.oktrueque.model.VerificationToken;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl  implements UserService{

    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           EmailService emailService){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }
    @Override
    public User addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Boolean checkIfUserExists(String email, String username){ return userRepository.checkIfUserExists(email, username)>0;}

    @Override
    public User getUserByEmailOrUsername(String email, String username){
        return userRepository.findByEmailOrUsername(email, username);
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public void sendVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepository.save(verificationToken);
        String uriConfirm = "http://localhost:8080/" + user.getUsername() +
                "/" + token + "/confirm";
        Email email = new Email();
        email.setMailTo(user.getEmail());
        email.setMailSubject("Validar cuenta");
        Map< String, Object > model = new LinkedHashMap<>();
        model.put("nombre", user.getName());
        model.put("apellido", user.getLast_name());
        model.put("uri_confirm", uriConfirm);
        email.setModel(model);
        emailService.sendMail(email,"confirmAccount.ftl");
    }

    @Override
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
