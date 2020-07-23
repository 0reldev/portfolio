package com.reldev.website.controllers;

import com.reldev.website.entities.User;
import com.reldev.website.repositories.*;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String getIndex(Model out) {

        out.addAttribute("experiences", experienceRepository.findAll());
        out.addAttribute("courses", courseRepository.findAll());
        return "/index";
    }

    @GetMapping("/admin")
    public String getAllDatas(Model out) {

        User user = userService.getLoggedUser();
        out.addAttribute("user", user);
        out.addAttribute("experiences", experienceRepository.findAll());
        out.addAttribute("courses", courseRepository.findAll());
        out.addAttribute("skills", skillRepository.findAll());
        out.addAttribute("achievements", achievementRepository.findAll());
        return "/admin";
    }

    @GetMapping("/login")
    public String getLogIn() {

        return "/login";
    }

    @GetMapping("/init")
    @ResponseBody
    public String getInit() {

        User user = new User();
        user.setPassword(passwordEncoder.encode("kiwi"));
/*
        TODO: secure the password
*/
        user.setRole("ROLE_ADMIN");
        user.setUsername("aurelien");

        userRepository.save(user);
        return "ok";
    }

}

