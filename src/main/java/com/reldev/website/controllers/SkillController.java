package com.reldev.website.controllers;

import com.reldev.website.entities.Course;
import com.reldev.website.entities.Skill;
import com.reldev.website.entities.User;
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
public class SkillController {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillRepository repository;

    @GetMapping("/skill")
    public String getSkill(Model out,
                                @RequestParam(required = false) Long id) {

        User user = userService.getLoggedUser();
        Skill skill = new Skill();
        if (id != null) {

            Optional<Skill> optionalSkill = repository.findById(id);
            if (optionalSkill.isPresent()) {

                skill = optionalSkill.get();
            }
        }
        out.addAttribute("user", user);
        out.addAttribute("skill", skill);
        return "/skill";
    }

    @PostMapping("/skill")
    public String postSkill(@ModelAttribute Skill skill) {

        repository.save(skill);
        return "redirect:/admin";
    }

    @GetMapping("/skill/delete")
    public String deleteSkill(@RequestParam Long id) {

        repository.deleteById(id);
        return "redirect:/admin";
    }

}