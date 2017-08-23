package com.oktrueque.controller;

import com.oktrueque.utils.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getLandingPage(Model model){
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contact")
    public ResponseEntity contactUs(@RequestBody Email email){
        return new ResponseEntity(HttpStatus.OK);
    }
}
