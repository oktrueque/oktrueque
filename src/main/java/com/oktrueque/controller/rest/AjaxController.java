package com.oktrueque.controller.rest;

import com.oktrueque.model.User;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Envy on 16/6/2017.
 */
@RestController
public class AjaxController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/checkUser")
    public String checkUser(@ModelAttribute User user){
        User usuario = userService.getUserByEmailOrUsername(user.getEmail(), user.getEmail());
        if(usuario != null && usuario.checkPassword(user.getPassword())){
            return usuario.getUsername();
        }else{
            return null;
        }
    }
}
