package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.model.UserTag;
import com.oktrueque.service.UserService;
import com.oktrueque.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

/**
 * Created by Tomas on 26-Jul-17.
 */
@Controller
public class TagController {

    private UserTagService userTagService;
    private UserService userService;

    @Autowired
    public TagController(UserTagService userTagService){
        this.userTagService = userTagService;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit/tags")
    public String getUserTags(Principal principal, Model model){
        User user = userService.getUserByUsername(principal.getName());
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("tags", tags);
        return "updateProfile";
    }

}
