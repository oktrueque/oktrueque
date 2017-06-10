package com.oktrueque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Envy on 10/6/2017.
 */
@Controller
public class TruequeController {

    @RequestMapping(method = RequestMethod.GET, value = "/trueques")
    private String getUsersItems(@RequestParam(value = "id_user_offerer") Integer idUserOfferer, @RequestParam(value = "id_user_demandant") String idUserDemandant, Model model){
        return "trueque";
    }
}
