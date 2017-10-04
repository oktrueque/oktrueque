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
    public ResponseEntity resetPassword(@RequestBody String datos){return userService.resetPassword(datos);}

    @RequestMapping(method = RequestMethod.GET, value = "/help")
    public String getHelpPage(Model model){
        return "help";
    }
}
