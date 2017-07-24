package com.oktrueque.controller;


import com.oktrueque.model.User;

import com.oktrueque.service.UserService;
import com.oktrueque.service.UserTagService;
import com.oktrueque.service.UserTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private UserTagService userTagService;

    @Autowired
    public UserController(UserService userService, UserTagService userTagService){
        this.userService = userService;
        this.userTagService = userTagService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String getUserById(Model model) {
        User user1 = new User();
        model.addAttribute("user", user1);
        return "/register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String addUser(Model model, @ModelAttribute @Valid User user, BindingResult result) {
        if (!user.checkUsername()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El username debe contener al menos 6 caracteres, sin caracteres especiales --");
            return "/register";
        }
        if (!user.checkEmail()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El email ingresado no es válido --");
            return "/register";
        }
        if (userService.checkIfUserExists(user.getEmail(), user.getUsername())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El email o username ingresado ya existen --");
            return "/register";
        }
        user.setStatus(0);
        user.setItemsAmount(0);
        user = userService.addUser(user);
        userService.sendVerificationToken(user);

        return "redirect:/users/" + user.getId();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    public String getUserProfile(Model model, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("items", user.getItems());
        model.addAttribute("comments", user.getComments());
        return "user";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/edit{username}")
    public String getUser(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/chats/{username}")
    public String getUser() {

        return "chat";
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
    public String updateUser(@ModelAttribute User user, @PathVariable String username) {
        user.setStatus(0);
        userService.updateUser(user);
        return "redirect:/users/" + username;
    }


    @RequestMapping("/login")
    public String loginUser() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }


    @RequestMapping(value="/{username}/{token}/confirm", method = RequestMethod.GET)
    @Transactional
    public String confirmAccount(@PathVariable String username,
                                 @PathVariable String token){
        if(!userService.confirmAcount(username,token)){
            return "badUrl";
        }
        return "redirect:/login";
    }


}