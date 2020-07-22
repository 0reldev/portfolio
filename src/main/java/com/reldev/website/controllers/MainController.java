package com.reldev.website.controllers;

import com.reldev.website.entities.User;
import com.reldev.website.repositories.CourseRepository;
import com.reldev.website.repositories.ExperienceRepository;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/")
    public String getIndex() {

        return "/index";
    }

    @GetMapping("/admin")
    public String getAllDatas(Model out) {

        User user = userService.getLoggedUser();
        out.addAttribute("user", user);
        out.addAttribute("experiences", experienceRepository.findAll());
        out.addAttribute("courses", courseRepository.findAll());
        return "/admin";
    }

    @GetMapping("/login")
    public String getLogIn() {

        return "/login";
    }

}

