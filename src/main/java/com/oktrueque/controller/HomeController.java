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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

        Map<String,Object> model = new LinkedHashMap<>();
        Random rand = new Random();

        if(datos.contains("@")){
            email = datos;
            User user = userService.getUserByEmailOrUsername(email, username);
            user.setPassword(email.substring(0,email.indexOf("@"))+rand.nextInt(1000));
            userService.updateUser(user);
            emailObject.setMailTo(email);
            emailObject.setMailSubject("OkTrueque - Reset password");
            model.put("user",user);
            emailObject.setModel(model);
            emailService.sendMail(emailObject,"passwordReset.ftl");
        } else {
            username = datos;
            User user = userService.getUserByEmailOrUsername(email, username);
            user.setPassword(username+rand.nextInt(1000));
            userService.updateUser(user);
            emailObject.setMailTo(user.getEmail());
            emailObject.setMailSubject("OkTrueque - Reset password");
            model.put("user",user);
            emailObject.setModel(model);
            emailService.sendMail(emailObject,"passwordReset.ftl");
        }


        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
