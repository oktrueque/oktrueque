package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String getUserById(Model model) {
        User user1 = new User();
        model.addAttribute("user", user1);
        return "/register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String addUser(Model model, @ModelAttribute User user) {
        user.setStatus(0);
        userService.addUser(user);
        return "redirect:/register";
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
//    public String getUserProfile(Model model, @PathVariable long id) {
//        User user = userService.getUserById(id);
//        model.addAttribute("user", user);
//        model.addAttribute("items", user.getItems());
//        return "userProfile";
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    public String getUserProfile(Model model, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("items", user.getItems());
        return "userProfile";
    }

//    @RequestMapping("/users/edit{username}")
//    public String getUser(@PathVariable long id, Model model) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "updateProfile";
//    }

    @RequestMapping(method = RequestMethod.GET, value ="/users/edit{username}")
    public String getUser(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "updateProfile";
    }

//    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
//    public String updateUser(@ModelAttribute User user, @PathVariable long id) {
//        user.setStatus(0);
//        userService.updateUser(user);
//        return "redirect:/users/" + id;
//    }
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


//    @RequestMapping(method = RequestMethod.POST, value = "/login")
//    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
//        User us;
//
//        if (userService.getUserByEmail(email) == null) {
//            us = userService.getUserByUserName(email);
//        } else us = userService.getUserByEmail(email);
//
//        if (us != null && us.getPassword().equals(password)) {
//
//            return "redirect:/users/" + us.getId();
//        } else {
//            model.addAttribute("loginError", true);
//            return "/login";
//        }
//    }

//    @RequestMapping(method = RequestMethod.POST, value = "/login")
//    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password,Model model){
//
//        User usuario = userService.getUserByEmailOrUsername(email, email);
//
//            if (usuario != null && usuario.getPassword().equals(password)){
//            return "redirect:/users/"+usuario.getId();
//            }
//
//            else {
//                model.addAttribute("loginError", true);
//
//                return "/login";
//            }
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password,Model model){

        User usuario = userService.getUserByEmailOrUsername(email, email);

        if (usuario != null && usuario.getPassword().equals(password)){
            return "redirect:/users/"+usuario.getUsername();
        }

        else {
            model.addAttribute("loginError", true);

            return "/login";
        }
    }

}
