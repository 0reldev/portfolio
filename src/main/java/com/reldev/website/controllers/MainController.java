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
    private SkillCategoryRepository skillCategoryRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AchievementCategoryRepository achievementCategoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getIndex(Model out) {

        out.addAttribute("homePage", true);
        out.addAttribute("aboutPage", false);
        out.addAttribute("cvPage", false);
        out.addAttribute("musicPage", false);
        out.addAttribute("contactPage", false);
        out.addAttribute("adminPage", false);
        return "/index";
    }

    @GetMapping("/admin")
    public String getAllDatas(Model out) {

        User user = userService.getLoggedUser();
        out.addAttribute("user", user);
        out.addAttribute("experiences", experienceRepository.findAll());
        out.addAttribute("courses", courseRepository.findAll());
        out.addAttribute("skills", skillRepository.findAll());
        out.addAttribute("skillCategories", skillCategoryRepository.findAllOrderedByName());
        out.addAttribute("achievements", achievementRepository.findAll());
        out.addAttribute("achievementCategories", achievementCategoryRepository.findAllOrderedByName());
        return "/admin";
    }

    @GetMapping("/login")
    public String getLogIn() {

        return "/login";
    }

    @GetMapping("/about")
    public String getAbout(Model out) {

        out.addAttribute("homePage", false);
        out.addAttribute("aboutPage", true);
        out.addAttribute("cvPage", false);
        out.addAttribute("musicPage", false);
        out.addAttribute("contactPage", false);
        out.addAttribute("adminPage", false);
        return "/about";
    }

    @GetMapping("/cv")
    public String getCV(Model out) {

        out.addAttribute("homePage", false);
        out.addAttribute("aboutPage", false);
        out.addAttribute("cvPage", true);
        out.addAttribute("musicPage", false);
        out.addAttribute("contactPage", false);
        out.addAttribute("adminPage", false);
        out.addAttribute("experiences", experienceRepository.findAllOrderedByDate());
        out.addAttribute("courses", courseRepository.findAllOrderedByDate());
        out.addAttribute("achievements", achievementRepository.findAllOrderedByDate());
        out.addAttribute("achievementCategories", achievementCategoryRepository.findAllOrderedByName());
        return "/cv";
    }

    @GetMapping("/music")
    public String getMusic(Model out) {

        out.addAttribute("homePage", false);
        out.addAttribute("aboutPage", false);
        out.addAttribute("cvPage", false);
        out.addAttribute("musicPage", true);
        out.addAttribute("contactPage", false);
        out.addAttribute("adminPage", false);
        return "/music";
    }

    @GetMapping("/contact")
    public String getContact(Model out) {

        out.addAttribute("homePage", false);
        out.addAttribute("aboutPage", false);
        out.addAttribute("cvPage", false);
        out.addAttribute("musicPage", false);
        out.addAttribute("contactPage", true);
        out.addAttribute("adminPage", false);
        return "/contact";
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

