package com.oktrueque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Envy on 10/7/2017.
 */
@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getLandingPage(Model model){
        return "index";
    }
}
