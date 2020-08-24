package com.reldev.website.controllers;

import com.reldev.website.entities.AchievementCategory;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.AchievementCategoryRepository;
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
public class AchievementCategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private AchievementCategoryRepository repository;

    @GetMapping("/achievement-category")
    public String getAchievementCategory(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        AchievementCategory achievementCategory = new AchievementCategory();
        if (id != null) {

            Optional<AchievementCategory> optionalAchievementCategory = repository.findById(id);
            if (optionalAchievementCategory.isPresent()) {

                achievementCategory = optionalAchievementCategory.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("achievement-category", achievementCategory);
        return "/achievement-category";
    }

    @PostMapping("/achievement-category")
    public String postAchievementCategory(@ModelAttribute AchievementCategory achievementCategory) {

        repository.save(achievementCategory);
        return "redirect:/admin";
    }

    @GetMapping("/achievement-category/delete")
    public String deleteAchievementCategory(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }
}
