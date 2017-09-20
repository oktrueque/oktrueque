package com.oktrueque.controller;

import com.oktrueque.model.Email;
import com.oktrueque.model.User;
import com.oktrueque.service.EmailService;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getLandingPage(Model model){
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact")
    public ResponseEntity contactUs(@RequestBody Email email){
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method= RequestMethod.POST, value = "/resetPassword")
    public ResponseEntity resetPassword(@RequestBody String datos){
        Email emailObject = new Email();
        String email = "", username = "";
        if(datos.contains("@")){
            email = datos;
            User user = userService.getUserByEmailOrUsername(email, username);
            user.setPassword(email.substring(0,email.indexOf("@"))+Math.random()*10000);
            userService.updateUser(user);
            emailObject.setMailTo(email);
            emailObject.setMailSubject("OkTrueque - Reset password");
            emailService.sendMail(emailObject,"passwordReset");
        } else {
            username = datos;
            User user = userService.getUserByEmailOrUsername(email, username);
            user.setPassword(username+Math.random()*10000);
            userService.updateUser(user);
            emailService.sendMail(emailObject,"passwordReset");
            emailObject.setMailTo(user.getEmail());
            emailObject.setMailSubject("OkTrueque - Reset password");
            emailService.sendMail(emailObject,"passwordReset");
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
