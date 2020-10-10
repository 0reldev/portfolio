package com.reldev.website.controllers;

import com.reldev.website.entities.Experience;
import com.reldev.website.entities.User;
import com.reldev.website.repositories.ExperienceRepository;
import com.reldev.website.repositories.SkillCategoryRepository;
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
public class ExperienceController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExperienceRepository repository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SkillCategoryRepository skillCategoryRepository;

    @GetMapping("/admin/experience")
    public String getExperience(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        Experience experience = new Experience();
        if (id != null) {

            Optional<Experience> optionalExperience = repository.findById(id);
            if (optionalExperience.isPresent()) {

                experience = optionalExperience.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("experience", experience);
        out.addAttribute("skillList", skillRepository.findAllOrderedByCategoryAndName());
        out.addAttribute("skillCategoryList", skillCategoryRepository.findAllOrderedByName());
        out.addAttribute("adminPage", true);
        out.addAttribute("subAdminPage", true);
        return "/admin/experience";
    }

    @PostMapping("/admin/experience")
    public String postExperience(@ModelAttribute Experience experience) {

        repository.save(experience);
        return "redirect:/admin";
    }

    @GetMapping("/admin/experience/delete")
    public String deleteExperience(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }

}
