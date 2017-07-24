package com.oktrueque.controller;

import com.oktrueque.model.Trueque;
import com.oktrueque.model.User;
import com.oktrueque.service.TruequeService;
import com.oktrueque.service.UserService;
import com.oktrueque.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

/**
 * Created by nicolas on 21/07/17.
 */
@Controller
public class ProfileController {

    private UserService userService;
    private TruequeService truequeService;

    @Autowired
    public ProfileController(UserServiceImpl userService, TruequeService truequeService){
        this.userService = userService;
        this.truequeService = truequeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String getProfile(Principal principal, Model model){
        User user = userService.getUserByUsername(principal.getName());
        List<Trueque> trueques = truequeService.findByUserOffererIdOrUserDemandantId(user.getId(), user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasItems", user.getItems()!=null ? true : false);
        model.addAttribute("user", user.getItems());
        model.addAttribute("hasTrueques", trueques != null ? true : false);
        model.addAttribute("trueques", trueques);
        return "profile";
    }
}
