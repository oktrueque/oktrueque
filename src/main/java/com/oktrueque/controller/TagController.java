package com.oktrueque.controller;

import com.oktrueque.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Tomas on 26-Jul-17.
 */
@Controller
public class TagController {

    private UserTagService userTagService;

    @Autowired
    public TagController(UserTagService userTagService){
        this.userTagService = userTagService;
    }




}
