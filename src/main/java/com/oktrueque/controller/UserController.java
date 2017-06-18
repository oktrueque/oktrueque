package com.oktrueque.controller;

import com.oktrueque.model.User;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.jasypt.util.password.BasicPasswordEncryptor;

@Controller
public class UserController {

    private UserService userService;
    private BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

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
        if(!user.checkUsername()){
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El username debe contener al menos 6 caracteres, sin caracteres especiales --");
            return "/register";
        }
        if(!user.checkEmail()){
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El email ingresado no es válido --");
            return "/register";
        }
        if(userService.checkIfUserExists(user.getEmail(), user.getUsername())){
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El email o username ingresado ya existen --");
            return "/register";
        }
        user.setStatus(0);
        user.setItemsAmount(0);
        user.setPassword(this.encrypt(user.getPassword()));
        user = userService.addUser(user);
        //Send email to user for email confirmation <-------
        return "redirect:/users/" + user.getId();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public String getUserProfile(Model model, @PathVariable long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("items", user.getItems());
        return "userProfile";
    }

    @RequestMapping("/users/edit{id}")
    public String getUser(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable long id) {
        user.setStatus(0);
        user.setPassword(this.encrypt(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/users/" + id;
    }


    @RequestMapping("/login")
    public String loginUser() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public String validateUser(@RequestParam("email") String email, @RequestParam("password") String password,Model model){

        User usuario = userService.getUserByEmailOrUsername(email, email);

            if (usuario != null && this.encryptor.checkPassword(password,encrypt(password))){
            return "redirect:/users/"+usuario.getId();
            }

            else {
                model.addAttribute("loginError", true);

                return "/login";
            }
    }

    public String encrypt(String password){
        return encryptor.encryptPassword(password);
    }

}
