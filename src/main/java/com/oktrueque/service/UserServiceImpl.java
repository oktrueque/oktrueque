package com.oktrueque.service;

import com.oktrueque.model.Email;
import com.oktrueque.model.User;
import com.oktrueque.model.UserLite;
import com.oktrueque.model.VerificationToken;
import com.oktrueque.repository.UserLiteRepository;
import com.oktrueque.repository.UserRepository;
import com.oktrueque.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class UserServiceImpl  implements UserService{

    @Value("${api.url}")
    private String urlServer;

    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final UserLiteRepository userLiteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           VerificationTokenRepository verificationTokenRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           EmailService emailService,
                           UserLiteRepository userLiteRepository){
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
        this.userLiteRepository = userLiteRepository;
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
        String uriConfirm = urlServer + user.getUsername() +
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
    public boolean confirmAccount(String username, String token) {
        VerificationToken tokenStored  = verificationTokenRepository.findByToken(token);
        User userStored = userRepository.findOne(tokenStored.getUser().getId());
        if (tokenStored == null || userStored == null || !userStored.getUsername().equals(username)){
            return false;
        }
        userStored.setStatus(1);
        return true;
    }

    public User getUserById(long id){
        return userRepository.findUserById(id);
    }

    public ResponseEntity resetPassword(String datos){
        Email emailObject = new Email();
        String email = "", username = "",rawPassword;
        Map<String,Object> model = new LinkedHashMap<>();
        Random rand = new Random();
        String editProfileUri = urlServer+ "profile/edit";
        if(datos.contains("@")){
            email = datos;
            rawPassword = email.substring(0,email.indexOf("@"))+rand.nextInt(10000);
            User user = userRepository.findByEmailOrUsername(email, username);
            if(user!=null){
                user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
                userRepository.save(user);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            emailObject.setMailTo(email);
            emailObject.setMailSubject("OkTrueque - Reset password");
            model.put("user",user);
            model.put("rawPassword",rawPassword);
            model.put("uri_editar_perfil",editProfileUri);
            emailObject.setModel(model);
            emailService.sendMail(emailObject,"passwordReset.ftl");
        } else {
            username = datos;
            rawPassword = username+rand.nextInt(10000);
            User user = userRepository.findByEmailOrUsername(email, username);
            if(user!=null){
                user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
                userRepository.save(user);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            emailObject.setMailTo(user.getEmail());
            emailObject.setMailSubject("OkTrueque - Reset password");
            model.put("user",user);
            model.put("rawPassword",rawPassword);
            model.put("uri_editar_perfil",editProfileUri);
            emailObject.setModel(model);
            emailService.sendMail(emailObject,"passwordReset.ftl");
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public UserLite getUserLiteById(Long userId) {
        return userLiteRepository.findUserById(userId);
    }

    @Override
    public UserLite getUserLiteByUsername(String name) {
        return userLiteRepository.findByUsername(name);
    }
}
