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
    public String addUser(Model model, @ModelAttribute User user)
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
        return "apps-profile";

    }


}
