package com.oktrueque.controller;

import com.oktrueque.model.Email;
import com.oktrueque.service.EmailService;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private final UserService userService;
    private final EmailService emailService;
    private final ItemService itemService;

    @Autowired
    public HomeController(UserService userService, EmailService emailService, ItemService itemService) {
        this.userService = userService;
        this.emailService = emailService;
        this.itemService = itemService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getLandingPage(Model model){
        model.addAttribute("items", itemService.getLatestCreatedActiveItems(new PageRequest(0,6)));
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact")
    public ResponseEntity contactUs(@RequestBody Email email){return emailService.contact(email);}

    @RequestMapping(method= RequestMethod.POST, value = "/resetPassword")
    public ResponseEntity resetPassword(@RequestBody String datos){return userService.resetPassword(datos);}

    @RequestMapping(method = RequestMethod.GET, value = "/help")
    public String getHelpPage(Model model){
        return "help";
    }




}
