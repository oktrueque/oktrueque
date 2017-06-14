package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public String getUserProfile(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("items", user.getItems());
        return "userProfile";
    }

    @RequestMapping("/users/edit{id}")
    public String getCategory(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public String updateCategory(@ModelAttribute User user, @PathVariable Long id) {
        userService.updateUser(user);
        return "redirect:/users/" + id;
    }
}
