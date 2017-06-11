package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Felipe on 7/5/2017.
 */
@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public String getUserById(Model model){
        User user1 = new User();
        model.addAttribute("user", user1);
        return "/users";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public String addUser(@ModelAttribute User user)
    {
        user.setStatus(0);
        userService.addUser(user);
        return "redirect:/users";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public String getUserProfile(Model model, @PathVariable long id)
    {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("items", user.getItems());
        return "userProfile";

    }

    // obtengo el usuario para poder upgradiarlo
    @RequestMapping("/users/edit{id}")
    public String getUser(@PathVariable long id, Model model){
        model.addAttribute("user" , userService.getUserById(id));
        return "updateProfile";

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable long id)
    {
        user.setStatus(0);
        userService.updateUser(user);
        return "redirect:/users/"+id;
    }


    @RequestMapping("/login")
    public String loginUser(){
        return "login";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password)
    {
        User us = userService.getUserByEmail(email);
        if (us!=null && us.getPassword().equals(password)) {

            return "correcto-login";
        }
        else return "error-login";
    }





}
