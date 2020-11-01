package com.reldev.website.controllers;

import com.reldev.website.entities.AchievementCategory;
import com.reldev.website.entities.SkillCategory;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.SkillCategoryRepository;
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
public class SkillCategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillCategoryRepository repository;

    @GetMapping("/admin/skill-category")
    public String getSkillCategory(Model out,
                                         @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        SkillCategory skillCategory = new SkillCategory();
        if (id != null) {

            Optional<SkillCategory> optionalSkillCategory = repository.findById(id);
            if (optionalSkillCategory.isPresent()) {

                skillCategory = optionalSkillCategory.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("skillCategory", skillCategory);
        out.addAttribute("adminPage", true);
        out.addAttribute("subAdminPage", true);
        return "/admin/skill-category";
    }

    @PostMapping("/admin/skill-category")
    public String postSkillCategory(@ModelAttribute SkillCategory skillCategory) {

        repository.save(skillCategory);
        return "redirect:/admin#skillCategoriesSection";
    }

    @GetMapping("/admin/skill-category/delete")
    public String deleteSkillCategory(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin#skillCategoriesSection";
    }
}
