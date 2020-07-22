package com.reldev.website.controllers;

import com.reldev.website.entities.Achievement;
import com.reldev.website.entities.Course;
import com.reldev.website.entities.Skill;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.AchievementRepository;
import com.reldev.website.repositories.CourseRepository;
import com.reldev.website.repositories.SkillRepository;
import com.reldev.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AchievementController {

    @Autowired
    private UserService userService;

    @Autowired
    private AchievementRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("/achievement")
    public String getAchievement(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        Achievement achievement = new Achievement();
        if (id != null) {

            Optional<Achievement> optionalAchievement = repository.findById(id);
            if (optionalAchievement.isPresent()) {

                achievement = optionalAchievement.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("achievement", achievement);
        out.addAttribute("skillList", skillRepository.findAll());
        return "/achievement";
    }

    @PostMapping("/achievement")
    public String postAchievement(@ModelAttribute Achievement achievement) {

        repository.save(achievement);
        return "redirect:/admin";
    }

    @GetMapping("/achievement/delete")
    public String deleteAchievement(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }

}
