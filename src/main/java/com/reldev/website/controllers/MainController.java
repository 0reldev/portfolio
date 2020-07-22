package com.reldev.website.controllers;

import com.reldev.website.entities.User;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String getIndex() {

        return "/index";
    }

    @GetMapping("/admin")
    public String getAdmin(Model out) {

        User user = userService.getLoggedUser();
        out.addAttribute("user", user);
        return "/admin";
    }

    @GetMapping("/login")
    public String getLogIn() {

        return "/login";
    }

}

