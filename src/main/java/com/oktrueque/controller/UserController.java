package com.oktrueque.controller;

import com.oktrueque.model.*;

import com.oktrueque.service.*;

import com.oktrueque.model.Comment;
import com.oktrueque.model.Item;
import com.oktrueque.model.User;
import com.oktrueque.model.UserTag;
import com.oktrueque.service.ItemService;
import com.oktrueque.service.UserService;
import com.oktrueque.service.UserTagService;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private UserTagService userTagService;
    private ItemService itemService;
    private ComplaintService complaintService;
    private ComplaintTypeService complaintTypeService;


    @Autowired
    public UserController(UserService userService, UserTagService userTagService, ItemService itemService, ComplaintService complaintService, ComplaintTypeService complaintTypeService) {
        this.userService = userService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.complaintService = complaintService;
        this.complaintTypeService = complaintTypeService;
    }



    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public String getUserById(Model model) {
        User user1 = new User();
        model.addAttribute("user", user1);
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public String addUser(Model model, @ModelAttribute @Valid User user, BindingResult result) {
        if (!user.checkUsername()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "-- El username no caracteres especiales --");
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
        user.setPhoto1(Constants.returnRandomImage());
        user = userService.addUser(user);
        userService.sendVerificationToken(user);
        return "/confirmEmail";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users/complaints/{username}")
    public ResponseEntity<Complaint> addComplaint(@RequestBody Complaint complaint, Principal principal, @PathVariable String username) {

        User userDemandant = userService.getUserByUsername(principal.getName());
        User userTarget = userService.getUserByUsername(username);
        complaint.setUser_target(userTarget);
        complaint.setUser_origin(userDemandant);
        complaint.setDate(LocalDateTime.now());
        complaintService.saveComplaint(complaint);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}")
    public String getUserProfile(Model model, @PathVariable String username, @PageableDefault(value = 2) Pageable pageable) {
        User user = userService.getUserByUsername(username);
        List<Item> items = itemService.getItemsByUserUsername(user.getUsername(), pageable);
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        List<Comment> comments = user.getComments();
        List<ComplaintType> complaintTypes = complaintTypeService.getComplaintTypes();
        Complaint complaint = new Complaint();
        model.addAttribute("complaintTypes", complaintTypes);
        model.addAttribute("complaint", complaint);
        model.addAttribute("user", user);
        model.addAttribute("hasItems", items.size() != 0 ? true : false);
        model.addAttribute("items", items);
        model.addAttribute("hasComments", comments.size() != 0 ? true : false);
        model.addAttribute("comments", comments);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "user";
    }



    @RequestMapping(method = RequestMethod.GET, value = "/users/chats/{username}")
    public String getUser() {
        return "chat";
    }

    @RequestMapping("/login")
    public String loginUser() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "index";
    }


    @RequestMapping(value="/{username}/{token}/confirm", method = RequestMethod.GET)
    @Transactional
    public String confirmAccount(@PathVariable String username,
                                 @PathVariable String token){
        if(!userService.confirmAccount(username,token)){
            return "error_500";
        }
        return "redirect:/login";
    }


}